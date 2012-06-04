package game;

import game.tile.Tile;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * Player in game.
 * @author Risto Novik
 *
 */
public class Player implements Serializable {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = 1154954097750497748L;
	/**
	 * User name in game.
	 */
	private String name;
	/**
	 * Unique code for each player.
	 */
	private UUID id;
	/**
	 * In which tile the player is.
	 */
	private Tile currentLocation;
	/**
	 * Previous tile.
	 */
	private Tile lastLocation;
	/**
	 * Color which used to draw player marks.
	 */
	private Color color;
	/**
	 * Store the count how many games has user win.
	 */
	private int win;
	/**
	 * If the player has to skip the next turn.
	 */
	private boolean nextTimeSkip;
	
	/**
	 * Creating new player object creates also unique id.
	 */
	public Player(String name) {
		this.name = name;
		this.color = new Color(
				new Random().nextInt() * 254);
	}
	
	/**
	 * Move player to tile which is specified steps away,
	 * if there's no more tiles to move use previous tile.
	 * @param steps Integer
	 */
	public void movePlayer(int steps) {
		Tile previous = getCurrentLocation(), 
			 from = getCurrentLocation();
		lastLocation = previous;
		
		for (int i = 0; from != null; i++) {
			if (i == steps) break; 
			previous = from;	
			from = from.getNext();
		}
		if (from == null) {
			from = previous;
		}
		setCurrentLocation(from);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public UUID getID() {
		return id;
	}

	public void setCurrentLocation(Tile currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Tile getCurrentLocation() {
		return currentLocation;
	}

	public Color getColor() {
		return color;
	}

	public void incWin() {
		this.win++;
	}

	public int getWin() {
		return win;
	}

	public void setNextTimeSkip(boolean nextTimeSkip) {
		this.nextTimeSkip = nextTimeSkip;
	}

	public boolean isNextTimeSkip() {
		return nextTimeSkip;
	}

	public void setLastLocation(Tile lastLocation) {
		this.lastLocation = lastLocation;
	}

	public Tile getLastLocation() {
		return lastLocation;
	}
}
