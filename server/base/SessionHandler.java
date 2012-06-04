package server.base;

import game.Board;
import game.GameState;
import game.Player;
import game.Turn;
import game.tile.SpecialTile;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

import server.SocketServer;

/**
 * One thread for each client(connection).
 * @author Risto Novik
 *
 */
public class SessionHandler extends Thread {
	/**
	 * Game board which is sent to player.
	 */
	private Board gameBoard;
	/**
	 * Connection socket.
	 */
	private Socket socket;
	/**
	 * Broadcasting instance.
	 */
	private Broadcaster broadcaster;
	/**
	 * Active sessions list.
	 */
	private ActiveSessions as;
	/**
	 * Flag for keeping record if it is first object sending.
	 */
	private boolean firstConnect = true;
	/**
	 * Last turn made by this player.
	 */
	private Turn lastTurn;
	/**
	 * Output stream to send objects to client.
	 */
	private ObjectOutputStream out;
	/**
	 * Input stream to read the Turn information from client.
	 */
	private ObjectInputStream in;
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
	/**
	 * Time out before the next game begins.
	 */
	private static final int NEXT_GAME = 15;
	
	/**
	 * Default constructor.
	 * @param socket Socket
	 */
	public SessionHandler(Socket socket) {
		this.socket = socket; 
	}
	/**
	 * {@link SocketHandler}
	 */
	public SessionHandler(Socket accept, Board gameBoard, Broadcaster broadcaster, ActiveSessions as) {
		this(accept);
		this.gameBoard = gameBoard;
		this.broadcaster = broadcaster;
		this.as = as;
	}

	@Override
	public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
 
            while (true) {
            	Turn turn = (Turn) in.readObject();
            	proccessTurn(turn);
            }
        } catch (EOFException e) { 
        	removeSession();
        } catch (SocketException e) {
            if ("Connection reset".equals(e.getMessage())) {
                logger.info("Client disconnected, performing cleanup");
                removeSession();
//                sendTheGameBoard();
            } else {
            	logger.warning("Connection between client lost " + Thread.currentThread());
            }
        } catch (Exception e) {
        	logger.warning("Error " + Thread.currentThread() + " message: " + e.getMessage());
            System.exit(-1);
        } finally {
        	if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					logger.warning("Socket closing failed");
				}
        }
	}
	
	/**
	 * Validates and checks the client sent Turn object.
	 * @param turn from client side 
	 */
	private void proccessTurn(Turn turn) {
		if (turn != null) {
    		if (firstConnect) {
    			// First object sharing, add the player
    			gameBoard.addPlayer(turn.getWhoseTurn());
    			lastTurn = turn;
    			firstConnect = false;
    		} else {
        		// Add the turn to game board and make validation
        		gameBoard.increaseTurns();
        		// Players are same the validation
        		if (gameBoard.getCurrentTurn().equals(turn.getWhoseTurn())) {
        			int score = turn.getDiceScore();
        			// Score must be between 0 and 6
        			if (score <= 6 && score >= 0) {
        				Player player = gameBoard.getPlayers().get(
        						gameBoard.getPlayers().indexOf(gameBoard.getCurrentTurn()));
        				
        				// Move only if we don't have to skip the move 
        				if (!player.isNextTimeSkip())
        					player.movePlayer(turn.getDiceScore());
        				
        				if (player.getCurrentLocation().equals(gameBoard.getEnd())) {
        					// Reached end, change game status
        					gameBoard.setGameState(GameState.END);
        					player.incWin();
        					restartGame();
        				} else {
        					skippingTurn(player);
        					// If not in end we can get next player.
        					gameBoard.nextPlayer();
        				}
        				gameBoard.setLastTurn(turn);
        			}
        		}
        		
    		}
    		// Send the game board to all the active sessions.
    		broadcaster.send();
    	}
	}
	
	/**
	 * If the player's on special tile then you will
	 * have to miss the turn.
	 * @param player Player
	 */
	private void skippingTurn(Player player) {
		if (player.getCurrentLocation() instanceof SpecialTile && !player.isNextTimeSkip()) {
			player.setNextTimeSkip(true);
		} else {
			player.setNextTimeSkip(false);
		}
	}
	
	/**
	 * Restarts the game, changes the game state and player
	 * locations.
	 */
	private void restartGame() {
		final Timer restartTimer = new Timer("Restart timer");
		restartTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isAlive()) {
					logger.info("Restart the game");
					// Set the game status back to playing
					gameBoard.setGameState(GameState.PLAYING);
					setPlayersBackToBeginning();
					broadcaster.send();
					restartTimer.cancel();
				}
			}
		}, NEXT_GAME * 1000);
	}
	
	/**
	 * Set the players locations to the first tile.
	 */
	private void setPlayersBackToBeginning() {
		for (Player player : gameBoard.getPlayers()) {
			player.setCurrentLocation(gameBoard.getStart());
			player.setNextTimeSkip(false);
		}
		// Set the last location to null so we don't need to draw arrow.
		gameBoard.getLastTurn().getWhoseTurn().setLastLocation(null);
		gameBoard.nextPlayer();
	}
	
	/**
	 * Remove the session and the player from game.
	 */
	private void removeSession() {
        as.removeSession(this);
        if (lastTurn.getWhoseTurn() != null)
        	gameBoard.removePlayer(lastTurn.getWhoseTurn());
       
	}
	
	/**
	 * Send the game board to player through object stream.
	 */
	public void sendTheGameBoard() {
		try {
			out.reset();
			out.writeObject(this.gameBoard);
			out.flush();
		} catch (IOException e) {
			logger.warning("Problem with sending game board object " + e.getMessage());
		}
	}
}
