package server;

import game.Board;
import game.GameState;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.logging.Logger;

import server.base.ActiveSessions;
import server.base.Broadcaster;
import server.base.SessionHandler;
 
/**
 * Server main start class.
 * @author Risto Novik
 *
 */
public class SocketServer {
	
	/**
	 * Logging the server information and errors in console.
	 */
    private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
    /**
     * Time when the server connections are accepted.
     */
	public static final int TIME_ACCEPT_CONNECTION = 10;
	/**
	 * On what port the server runs.
	 */
	private static final int PORT = 1234;
	/**
	 * How many parallel connection can be there
	 */
	private static final int MAX_SESSIONS = 10;
	/**
	 * Hold and create the game board object.
	 */
	private Board gameBoard = new Board();
	/**
	 * Active sessions and known as players who are connected to game.
	 */
	private ActiveSessions sessions = new ActiveSessions();
	/**
	 * Sharing objects with active sessions.
	 */
	private Broadcaster broadcaster = new Broadcaster(sessions, gameBoard);
	/**
	 * Server socket object.
	 */
    private ServerSocket socket;
	
    /**
     * Starts the listening for connections.
     */
	public void startServer() {
		try {
			socket = new ServerSocket(PORT);
			// Timeout after what no more new connections are not accepted.
	        socket.setSoTimeout(TIME_ACCEPT_CONNECTION * 1000); 
	        logger.info("Server started on port " + socket.getLocalPort());
	        
	        while (true) {
	            SessionHandler session = new SessionHandler(socket.accept(), gameBoard, broadcaster, sessions);
	            sessions.addSession(session);
	            session.start();
	            if (sessions.getCount() >= MAX_SESSIONS)
	            	throw new SocketTimeoutException();
	        }
	        
		} catch (SocketTimeoutException e1) {
			logger.info("No more new connecions are accpeted, start game or end");
			// Start game only if there's more then 1 player
			if (sessions.getCount() > 0) {
				gameBoard.setCurrentTurn(gameBoard.getPlayers().get(0));
				gameBoard.setGameState(GameState.PLAYING);
				gameBoard.setStartGame(new Date(System.currentTimeMillis()));
				logger.info("Set the gamestate to " + gameBoard.getGameState());
				broadcaster.send();
			}
		} catch (IOException e) {
			logger.info("I/O error " + e.getMessage());
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
	 * Main program.
	 * @param args String array
	 */
    public static void main(String[] args) {
    	SocketServer server = new SocketServer();
    	server.startServer();
    }
}