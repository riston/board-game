package game;

import java.io.Serializable;

public class Turn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9218710539359281798L;

	private int diceScore;

	private Player whoseTurn;
	
	public void setDiceScore(int diceScore) {
		this.diceScore = diceScore;
	}

	public int getDiceScore() {
		return diceScore;
	}

	public void setWhoseTurn(Player whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	public Player getWhoseTurn() {
		return whoseTurn;
	}
	
}

