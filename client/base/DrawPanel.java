package client.base;

import game.Board;
import game.Player;
import game.Turn;
import game.tile.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel for drawing board.
 * @author Risto Novik
 *
 */
public class DrawPanel extends JPanel {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = 9015978609874488511L;
	/**
	 * Game board.
	 */
	private Board gameBoard;
	/**
	 * Background image.
	 */
	private Image image;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		addBackground(g2);
		
		if (gameBoard != null) {
			switch (gameBoard.getGameState()) {
				case WAITING_PLAYERS:
					drawWaitingPlayersScreen(g2);	
					break;
		
				case PLAYING:
					drawBoard(g2, gameBoard);
					break;
					
				case END:
					drawEndScreen(g2);
					break;
				
				default:
					break;
			}
		}
	}
	
	/**
	 * Load the background image.
	 * @param g2 Graphics2D
	 */
	private void addBackground(Graphics2D g2) {
		if (image == null) 
			image = new ImageIcon(this.getClass().getResource("/client/images/circus.jpg")).getImage();
		g2.drawImage(image, 0, 0, null);
	}
	
	/**
	 * Waiting screen before the game starts.
	 * @param g2 Graphics2D
	 */
	private void drawWaitingPlayersScreen(Graphics2D g2) {
		Font font = new Font("sansserif", Font.TRUETYPE_FONT, 50);
		g2.setFont(font);
		g2.setPaint(Color.white);
		g2.drawString("Waiting for players...", 50, 60);
	}
	
	/**
	 * End screen after player has reached end.
	 * @param g2 Graphics2D
	 */
	private void drawEndScreen(Graphics2D g2) {
		Font font = new Font("sansserif", Font.TRUETYPE_FONT, 50);
		g2.setFont(font);
		g2.setPaint(Color.white);
		g2.drawString("Well we have winner!", 50, 60);
		g2.drawString("Player " + gameBoard.getCurrentTurn().getName(), 50, 120);
		font = new Font("sansserif", Font.TRUETYPE_FONT, 36);
		g2.setFont(font);
		g2.drawString("Next game will begin soon...", 50, 180);
		
	}
	
	/**
	 * Board drawing, begins with 
	 * <ol>
	 * <li>Drawing connections between tiles</li>
	 * <li>Drawing tiles</li>
	 * <li>Last move drawn as arrow</li> 
	 * </ol>
	 * @param g2 Graphics@D
	 * @param board Board
	 */
	private void drawBoard(Graphics2D g2, Board board) {
		Tile tile = board.getStart(), previous = board.getStart();
		// Draw connecting line
		while (tile != null) {
			g2.setColor(Color.white);
			g2.drawLine(previous.getX() + previous.getTileSize() / 2, previous.getY() + previous.getTileSize() / 2, 
					tile.getX() + tile.getTileSize() / 2, tile.getY() + tile.getTileSize() / 2);
			previous = tile;
			tile = tile.getNext();
		}
		
		// Draw tiles
		tile = board.getStart();
		while (tile != null) {
			tile.drawTile(g2);
			tile.drawPlayers(g2, gameBoard.playersOnTile(tile));
			tile = tile.getNext();
		}
		if (board.getLastTurn() != null)
			drawLastMove(g2, board.getLastTurn());
	}
	
	/**
	 * Draw the player movement from last tile.
	 * @param g2 Graphics2D
	 * @param lastTurn Tile
	 */
	private void drawLastMove(Graphics2D g2, Turn lastTurn) {
		Player p = lastTurn.getWhoseTurn();
		Tile current = p.getCurrentLocation();
		Tile last = p.getLastLocation();
		
		if (last == null || current == null || last.equals(current)) 
			return;
		
		int tileSize = last.getTileSize() / 2;
		int x1 = last.getX() + tileSize;
		int y1 = last.getY() + tileSize;
		
		tileSize = current.getTileSize() / 2;
		int x2 = current.getX() + tileSize;
		int y2 = current.getY() + tileSize;
		
		Color c = p.getColor();
		g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 200));
		drawArrow(g2, x1, y1, x2, y2);
	}

	/**
	   * Draws an arrow on the given Graphics2D context.
	   * Code taken from site http://www.bytemycode.com/snippets/snippet/82/
	   * Modified by Risto.
	   * @param g The Graphics2D context to draw on
	   * @param x The x location of the "tail" of the arrow
	   * @param y The y location of the "tail" of the arrow
	   * @param xx The x location of the "head" of the arrow
	   * @param yy The y location of the "head" of the arrow
	   */
	private void drawArrow(Graphics2D g, int x, int y, int xx, int yy) {
		float arrowWidth = 7.0f;
		float theta = 0.423f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength, th, ta, baseX, baseY;

		xPoints[0] = xx;
		yPoints[0] = yy;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - x;
		vecLine[1] = (float) yPoints[0] - y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1]
				* vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		float dash1[] = {10.0f};
		BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	    g.setStroke(dashed);
		g.draw(new Line2D.Float(x, y, (int) baseX, (int) baseY));
		g.fillPolygon(xPoints, yPoints, 3);
	}
	
	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
	}

	public Board getGameBoard() {
		return gameBoard;
	}
}
