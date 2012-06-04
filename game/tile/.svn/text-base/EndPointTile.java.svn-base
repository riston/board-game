package game.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * End point tiles are meant to be used as the start point or end.
 * @author Risto Novik
 *
 */
public class EndPointTile extends Tile {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Tile}
	 */
	public EndPointTile(String name, int x, int y) {
		super(name, x, y);
		tileSize = 45;
		smallDescription = "start or end point";
	}
	
	@Override
	public void drawTile(Graphics2D g2) {
		GradientPaint gradient = new GradientPaint(getX(), getY(), Color.white, getX() + tileSize, getY() + tileSize,
		        Color.blue);
		Ellipse2D shape = new Ellipse2D.Double (getX(), getY(), tileSize, tileSize);
	    g2.setPaint (gradient);
	    g2.fill(shape);

	    // Change color and draw the shape, the
	    g2.setStroke (new BasicStroke(3));
	    g2.setPaint (Color.blue);
	    g2.draw(shape);
	}

}
