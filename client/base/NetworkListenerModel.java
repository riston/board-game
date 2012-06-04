package client.base;

import game.Player;
import game.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Client side communication is done here.
 * @author Risto Novik
 *
 */
public class NetworkListenerModel extends Observable implements Runnable {
	/**
	 * The host address.
	 */
	private static final String HOST_ADDRESS = "localhost";
	/**
	 * Port on what connection can be made.
	 */
	private static final int PORT = 1234;
	/**
	 * Client side socket.
	 */
	private Socket socket;
	/**
	 * Stream for writing Turn object..
	 */
    private ObjectOutputStream out = null;
    /**
     * Stream for reading Board object.
     */
    private ObjectInputStream in = null;
    /**
     * Client player.
     */
    private Player player;
    /**
     * If the object receving thread is active or not.
     */
    private boolean running = true;
    
    /**
     * Default constructor.
     * @param o Observer
     * @param player Player
     */
    public NetworkListenerModel(Observer o, Player player) {
    	this.addObserver(o);
        this.player = player;
        new Thread(this).start();
    }
 
    @Override
    public void run() {
    	try {
    		socket = new Socket(HOST_ADDRESS, PORT);
	        out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem occurd on connecting: \n" + e.getMessage());
			System.exit(-1);
		}
    	registerConnectingPlayer(player);
    	
        synchronized (this) {
            while (running) {
                try {
                	Object ob;
                	if ((ob = in.readObject()) != null) {
                        this.setChanged();
                        this.notifyObservers(ob);
                	}
                    
                } catch (ClassNotFoundException ignored) {
                } catch (IOException ignored) {
                	// End the thread as there's no messages to listen
                	return;
                }
            }
        }
    }
    
    /**
     * Sends the connecting object to server.
     * @param player Player
     */
    public void registerConnectingPlayer(Player player) {
    	Turn turn = new Turn();
    	turn.setWhoseTurn(player);
    	try {
			out.writeObject(turn);
			out.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null, "No luck on connecting to game, try again later!");
		}
    }
    
    /**
     * Send the dice rolling to server.
     * @param noScore boolean if no score then true
     */
    public void diceRoll(boolean noScore) {
    	Turn turn = new Turn();
    	int score = (noScore) ? 0 : new Random().nextInt(6) + 1;
    	
    	turn.setDiceScore(score);
    	turn.setWhoseTurn(player);
    	try {
			out.writeObject(turn);
			out.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Seems like there's problem on connection!");
		}
    }
    
    /**
     * Close the socket. 
     */
    public void close() {
    	try {
			socket.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Closing the socket failed!");
		}
    }
    
    /**
     * Stop the listening thread.
     */
    public void stop() {
    	running = false;
    }
}

