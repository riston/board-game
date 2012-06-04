package server.base;

import game.Board;

import java.util.Iterator;
import java.util.logging.Logger;

import server.SocketServer;

/**
 * Server broadcaster object sends the serialized objects
 * to active clients.
 * @author Risto Novik
 *
 */
public class Broadcaster {
	/**
	 * Server logger.
	 */
	private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
	/**
	 * Broadcast frequency in seconds.
	 */
	protected static final int BROADCAST_FREQ = 10;
	/**
	 * Instance to the active sessions.
	 */
	private ActiveSessions activeSessions;
	
	/**
	 * Default constructor also sets the broadcast thread
	 * which sends the object automatically to active sessions.
	 * @param aa {@link ActiveSessions}
	 * @param board {@link Board}
	 */
	public Broadcaster(ActiveSessions aa, Board board) {
		this.activeSessions = aa;
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// Wait before the server accepts connections
				try {
					Thread.sleep(SocketServer.TIME_ACCEPT_CONNECTION * 1000 + 2000);
				} catch (InterruptedException e1) {
					logger.info("Broadcaster sleep failed");
				}
				while (activeSessions.getCount() > 0) {
					try {
						Thread.sleep(BROADCAST_FREQ * 1000);
					} catch (InterruptedException e) {
						logger.info("Broadcaster sleep failed");
					}
					send();
				}
			}
		});
		t.start();
	}
	
	/**
	 * Send the object to all the listed sessions.
	 */
	public void send() {
		// Broadcast board forever
		synchronized (activeSessions) {
			Iterator<SessionHandler> active = activeSessions.iterator();

			while (active.hasNext()) {
				SessionHandler session = active.next();

				if (!session.isAlive()) {
					active.remove();
					session.interrupt();
				} else {
					session.sendTheGameBoard();
				}
			}
		}

	}
}