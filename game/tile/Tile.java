package game.tile;

import game.Player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tile on the board.
 * @author Risto Novik
 *
 */
public abstract class Tile implements Serializable, TileDraw  {
	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = 4940210939937844289L;
	/**
	 * Custom name for the tile.
	 */
	private String name;
	/**
	 * x and y custom location.
	 */
	private int x, y;
	/**
	 * The next tile object, if end then null.
	 */
	private Tile next;
	/**
	 * The drawing size on board.
	 */
	protected int tileSize = 30;
	/**
	 * Information what player has to do when on this tile.
	 */
	protected String smallDescription = "";
	
	/**
	 * Default constructor.
	 * @param name String tile name
	 * @param x Integer location x
	 * @param y Integer location y
	 */
	public Tile(String name, int x, int y) {
		this.setName(name);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This how the players should be drawn on tile.
	 * @param g2 Graphics2D
	 * @param players ArrayList of Player
	 */
	public void drawPlayers(Graphics2D g2, ArrayList<Player> players) {
		if (players.size() > 0) {
			for (int j = players.size(); j > 0; j--) {
				g2.setColor(players.get(j - 1).getColor());
				g2.setStroke (new BasicStroke(j * 6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				
				int radius = tileSize / 2;
				double x = radius * Math.cos(Math.toRadians(45));
				double y = radius * Math.sin(Math.toRadians(45));
				
				// Draw first line
				g2.draw(new Line2D.Float(
					getX() + radius - (int) x, 
					getY() + radius - (int) y, 
					getX() + radius + (int) x, 
					getY() + radius + (int) y));
				
				// Draw second line
				g2.draw(new Line2D.Float(
						getX() + radius + (int) x, 
						getY() + radius - (int) y, 
						getX() + radius - (int) x, 
						getY() + radius + (int) y));
			}
			
			g2.setColor(Color.black);
		}
	}
	

	/**
	 * @return the smallDescription
	 */
	public String getSmallDescription() {
		return smallDescription;
	}
	
	/**
	 * Add new tile and connect it to next.
	 * @param newTile Tile
	 * @return Tile
	 */
	public Tile create(Tile newTile) {
		this.next = newTile;
		return newTile;
	}

	public void setNext(Tile next) {
		this.next = next;
	}
	
	public Tile getNext() {
		return next;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
