package game.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Special tile, when the player's on that he will have to miss the turn.
 * @author Risto Novik
 *
 */
public class SpecialTile extends Tile {
	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = 978673825639059903L;
	
	/**
	 * {@linkplain Tile}
	 */
	public SpecialTile(String name, int x, int y) {
		super(name, x, y);
		smallDescription = "your on special tile you have to skip next time turn";
	}


	@Override
	public void drawTile(Graphics2D g2) {
		GradientPaint gradient = new GradientPaint(getX(), getY(), Color.white, getX() + tileSize, getY() + tileSize,
		        Color.orange);
		
		Ellipse2D shape = new Ellipse2D.Double (getX(), getY(), tileSize, tileSize);
	    g2.setPaint (gradient);
	    g2.fill(shape);

	    // Change color and draw the shape, the
	    g2.setStroke (new BasicStroke(3));
	    g2.setPaint (Color.green);
	    g2.draw(shape);

	}

}
