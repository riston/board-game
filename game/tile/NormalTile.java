package game.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Normal tile no effect to the player.
 * @author Risto Novik
 *
 */
public class NormalTile extends Tile {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = -4683203169940453874L;

	/**
	 * {@link Tile}
	 */
	public NormalTile(String name, int x, int y) {
		super(name, x, y);
		tileSize = 30;
		smallDescription = "normal tile";
	}

	@Override
	public void drawTile(Graphics2D g2) {
		Ellipse2D shape = new Ellipse2D.Double (getX(), getY(), tileSize, tileSize);
	    g2.setPaint (Color.green);
	    g2.fill(shape);

	    // Change color and draw the shape, the
	    g2.setStroke (new BasicStroke(3));
	    g2.setPaint (Color.white);
	    g2.draw(shape);
	    
		
		g2.setFont(new Font("Serif", Font.ITALIC, 9));
		g2.drawString(getName(), getX() + (tileSize / 2), getY() + (tileSize / 2 - 18));
		g2.setColor(Color.black);

	}

}
