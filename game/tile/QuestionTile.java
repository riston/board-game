package game.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * If the player is on this tile, there will be random question 
 * before player can move further. 
 * @author Risto Novik
 *
 */
public class QuestionTile extends Tile {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = -2290672383041073343L;
	/**
	 * List of the possible questions.
	 */
	private List<QuestionEntry> questions = new ArrayList<QuestionEntry>();
	/**
	 * {@link Tile}
	 */
	public QuestionTile(String name, int x, int y) {
		super(name, x, y);
		tileSize = 35;
		smallDescription = "next time question will be asked";
		addQuestions();
	}
	
	/**
	 * All the questions will be added here
	 */
	private void addQuestions() {
		questions.add(new QuestionEntry(" If you yelled for 7 years, would you have produced enough sound energy to heat one cup of coffee? ", JOptionPane.NO_OPTION));
		questions.add(new QuestionEntry(" If you fart consistently for 7 years, is enough gas produced to create the energy of an atomic bomb? ", JOptionPane.YES_OPTION));
		questions.add(new QuestionEntry(" Does banging your head against a wall use 150 calories in a minute? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Is the strongest muscle in the body the tongue? ", JOptionPane.YES_OPTION));
		questions.add(new QuestionEntry(" Do on average right-handed people live longer than left-handed people? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Can ants lift 70 times it's own weight? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Are polar bears left handed? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Does the catfish have over 40,000 taste buds? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Can the flea jump 350 times its body length? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Will a cockroach starve to death if it's head is cut off? ", JOptionPane.YES_OPTION));
		questions.add(new QuestionEntry(" Do some lions mate over 70 times a day? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" DO butterflies taste with their tail? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Is an ostrich's eye is bigger than it's brain? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Does a starfish have brain? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Does the mosquito repellents spray block the mosquito's sensors so they don't know you're there? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Can liquid inside young coconuts be used as substitute for blood plasma? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Can piece of paper be folded in half more than 7 times? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Do plane crashes kill more people annually than donkeys? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Do you burn more calories sleeping than you do watching television? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" DO oak trees produce acorns before they are fifty years old? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Was the first product to have a bar code a chewing gum? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Is the king of hearts the only king without a mustache? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Is a Boeing 747s wingspan longer than the Wright brother's first flight? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Did American Airlines save $80,000 in 1987 by eliminating 1 olive from each salad served in first-class? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Is Venus the only planet in our solar system that rotates clockwise? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Is coffee more efficient than apples at waking you up in the morning? ", JOptionPane.NO_OPTION));
		questions.add(new QuestionEntry(" Are the plastic things on the end of shoelaces are called aglagets? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Are most dust particles in your house from space? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Did the first owner of the Marlboro Company die of lung cancer? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Does Michael Jordan make as much money from Nike annually as all of the Nike factory workers in Malaysia combined? ", JOptionPane.NO_OPTION));
		questions.add(new QuestionEntry(" Was Walt Disney afraid of ducks? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Do pearls melt in vinegar? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Is the most valuable brand name on earth Coca-Cola? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Is it possible to lead a cow upstairs? ", JOptionPane.YES_OPTION));
		questions.add(new QuestionEntry(" Is it possible to lead a cow downstairs? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" A duck's quack doesn't echo, true? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Was William Jefferson Clinton the first US president whose name contained all the letters from the word 'criminal'? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Does a hurricane release more energy in 1 minutes than all of the world's nuclear weapons combined? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Do on average 100 people choke to death on ball-point pens every year? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Do on average people fear death more than spiders? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Ninety percent of New York City cabbies are recently arrived immigrants, true? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Are the elephants the only animals that can't jump? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" Will only one person in four billion live to be 116 or older? ", JOptionPane.NO_OPTION)); 
		questions.add(new QuestionEntry(" Do women blink nearly three times as much as men? ", JOptionPane.NO_OPTION));
		questions.add(new QuestionEntry(" Can a snail sleep for three years? ", JOptionPane.YES_OPTION)); 
		questions.add(new QuestionEntry(" No word in the English language rhymes with 'MONTH', true? ", JOptionPane.YES_OPTION)); 
	}
	
	/**
	 * Get the random question to player.
	 * @return {@link QuestionEntry} random question.
	 */
	public QuestionEntry getRandomQuestion() {
		return questions.get(new Random().nextInt(questions.size()));
	}

	@Override
	public void drawTile(Graphics2D g2) {
		GradientPaint gradient = new GradientPaint(getX(), getY(), Color.white, 
				getX() + tileSize, getY() + tileSize, Color.red);
		
		Ellipse2D shape = new Ellipse2D.Double (getX(), getY(), tileSize, tileSize);
	    g2.setPaint (gradient);
	    g2.fill(shape);

	    // Change color and draw the shape, the
	    g2.setStroke (new BasicStroke(3));
	    g2.setPaint (Color.green);
	    g2.draw(shape);
	    
	    g2.setColor(Color.black);
	    g2.drawString("?", getX() + tileSize / 2, getY() + tileSize / 2);
	}
	

}

