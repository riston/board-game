package game.tile;

import java.io.Serializable;

/**
 * Entry of the question and the answer.
 * @author Risto Novik
 *
 */
public class QuestionEntry implements Serializable {
	
	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = -2950936301108087829L;
	/**
	 * Question.
	 */
	private String question;
	/**
	 * The correct answer as the JOptionPane.YES or NO.
	 */
	private int answer;
	/**
	 * Constructor.
	 * @param question String
	 * @param answer Integer
	 */
	public QuestionEntry(String question, int answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public String getQuestion() {
		return question;
	}
}
