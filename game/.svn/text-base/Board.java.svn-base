package game;

import game.tile.EndPointTile;
import game.tile.NormalTile;
import game.tile.QuestionTile;
import game.tile.SpecialTile;
import game.tile.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Game board object holds the game tiles and players.
 * 
 * @author Risto Novik
 * 
 */
public class Board implements Serializable {

	/**
	 * Serial code.
	 */
	private static final long serialVersionUID = -5438023777500006080L;
	/**
	 * Tile where players starts the game.
	 */
	private Tile start;
	/**
	 * Tile where the game ends.
	 */
	private Tile end;
	/**
	 * Holds the game state, can be waiting players, playing and end.
	 */
	private GameState gameState = GameState.WAITING_PLAYERS;
	/**
	 * Currently related players who are playing.
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	/**
	 * Player whose turn is.
	 */
	private Player currentTurn;
	/**
	 * Last turn made.
	 */
	private Turn lastTurn;
	/**
	 * Counter how many turns there has been made.
	 */
	private int turnsMade = 0;
	/**
	 * Time when the game started.
	 */
	private Date startGame;

	/**
	 * Create basic board.
	 */
	public Board() {
		start = new EndPointTile("Start", 30, 20);
		end = new EndPointTile("End", 550, 392);

		start.create(new NormalTile("1", 90, 35))
				.create(new NormalTile("2", 140, 40))
				.create(new NormalTile("3", 190, 50))
				.create(new QuestionTile("4", 240, 55))
				.create(new QuestionTile("5", 290, 50))
				.create(new SpecialTile("6", 340, 40))
				.create(new NormalTile("7", 390, 25))
				.create(new NormalTile("8", 445, 10))
				.create(new NormalTile("9", 500, 30))
				.create(new NormalTile("10", 540, 65))
				.create(new NormalTile("11", 540, 105))
				.create(new QuestionTile("12", 500, 130))
				// first turn starts here
				.create(new NormalTile("13", 450, 150))
				.create(new NormalTile("14", 395, 130))
				.create(new NormalTile("15", 345, 110))
				.create(new NormalTile("16", 290, 95))
				.create(new NormalTile("17", 235, 110))
				.create(new QuestionTile("18", 177, 125))
				.create(new SpecialTile("19", 125, 110))
				.create(new NormalTile("20", 80, 125))
				.create(new NormalTile("21", 40, 150))
				.create(new NormalTile("22", 40, 190))
				// Second turn to down
				.create(new NormalTile("23", 80, 210))
				.create(new NormalTile("24", 125, 230))
				.create(new NormalTile("25", 180, 210))
				.create(new NormalTile("26", 235, 190))
				.create(new QuestionTile("27", 290, 170))
				.create(new QuestionTile("28", 345, 185))
				.create(new QuestionTile("29", 395, 200))
				.create(new NormalTile("30", 450, 210))
				.create(new NormalTile("31", 500, 230))
				.create(new NormalTile("32", 540, 265))
				.create(new QuestionTile("33", 537, 305))
				.create(new NormalTile("34", 500, 330))
				.create(new NormalTile("35", 450, 345))
				.create(new QuestionTile("36", 395, 330))
				.create(new SpecialTile("37", 345, 310))
				.create(new NormalTile("38", 290, 295))
				.create(new NormalTile("39", 235, 275))
				.create(new SpecialTile("40", 190, 290))
				.create(new NormalTile("41", 150, 325))
				.create(new NormalTile("42", 150, 365))
				.create(new NormalTile("43", 190, 390))
				.create(new QuestionTile("44", 240, 400))
				.create(new SpecialTile("45", 290, 403))
				.create(new QuestionTile("46", 340, 400))
				.create(new NormalTile("47", 390, 403))
				.create(new QuestionTile("48", 440, 400))
				.create(new NormalTile("49", 495, 403)).create(end);
		end.setNext(null);

	}

	/**
	 * Gives the turn to next player in ArrayList of players.
	 */
	public synchronized void nextPlayer() {
		if (players.size() > 0 && currentTurn != null) {
			int index = players.indexOf(currentTurn);
			if ((index + 1) >= players.size()) {
				// There's not so many players we have to take first
				currentTurn = players.get(0);
			} else {
				currentTurn = players.get(index + 1);
			}
		}
	}

	/**
	 * Get all players who are on the tile.
	 * 
	 * @param tile Tile
	 * @return ArrayList of players
	 */
	public synchronized ArrayList<Player> playersOnTile(Tile tile) {
		ArrayList<Player> playersWhoAre = new ArrayList<Player>();
		for (Player player : players) {
			if (player.getCurrentLocation().equals(tile))
				playersWhoAre.add(player);
		}
		return playersWhoAre;
	}

	/**
	 * Add the player into list and set the location to beginning.
	 * 
	 * @param player Player
	 */
	public synchronized void addPlayer(Player player) {
		// all players will be added to beginning
		player.setCurrentLocation(start);
		players.add(player);
	}

	/**
	 * Remove the player and if the turn has the same player gives it to next.
	 * 
	 * @param player Player
	 */
	public synchronized void removePlayer(Player player) {
		if (currentTurn != null && currentTurn.equals(player)) {
			if (players.size() > 1)
				nextPlayer();
		}
		players.remove(player);
	}

	/**
	 * Get the players in game.
	 * 
	 * @return ArrayList of players
	 */
	public synchronized ArrayList<Player> getPlayers() {
		return players;
	}

	public int getPlayerCount() {
		return players.size();
	}

	public void setStart(Tile start) {
		this.start = start;
	}

	public void increaseTurns() {
		this.turnsMade++;
	}

	public int getTurns() {
		return turnsMade;
	}

	public Tile getStart() {
		return start;
	}

	public Tile getEnd() {
		return end;
	}

	public void setEnd(Tile end) {
		this.end = end;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setCurrentTurn(Player currentTurn) {
		this.currentTurn = currentTurn;
	}

	public Player getCurrentTurn() {
		return currentTurn;
	}

	public void setLastTurn(Turn lastTurn) {
		this.lastTurn = lastTurn;
	}

	public Turn getLastTurn() {
		return lastTurn;
	}

	public void setStartGame(Date startGame) {
		this.startGame = startGame;
	}

	public Date getStartGame() {
		return startGame;
	}
}
