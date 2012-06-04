package server.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import server.SocketServer;

/**
 * Connected active sessions.
 * @author Risto Novik
 *
 */
public class ActiveSessions {
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
	/**
	 * Session list.
	 */
	private Collection<SessionHandler> sessionList = new ArrayList<SessionHandler>();

	/**
	 * Add new session.
	 * @param SessionHandler
	 */
	public synchronized void addSession(SessionHandler s) {
		sessionList.add(s);
	}

	/**
	 * Iterator for list.
	 * @return Iterator
	 */
	public Iterator<SessionHandler> iterator() {
		return sessionList.iterator();
	}
	
	/**
	 * How many active sessions there is.
	 * @return
	 */
	public synchronized int getCount() {
		return sessionList.size();
	}
	
	/**
	 * Remove session.
	 * @param session
	 */
	public synchronized void removeSession(SessionHandler session) {
		logger.info("Session removed " + session);
		sessionList.remove(session);
	}
}
