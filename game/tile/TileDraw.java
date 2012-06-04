package game.tile;

import java.awt.Graphics2D;

/**
 * Implementation how the tile should be drawn.
 * @author Risto Novik
 *
 */
public interface TileDraw {

	/**
	 * How the tile will be drawn on board.
	 * @param g2 Graphics2D object
	 */
	public abstract void drawTile(Graphics2D g2);
}
