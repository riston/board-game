package client;

import game.Board;
import game.GameState;
import game.Player;
import game.Turn;
import game.tile.QuestionEntry;
import game.tile.QuestionTile;
import game.tile.Tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import client.base.DrawPanel;
import client.base.GameStatisticsLog;
import client.base.NetworkListenerModel;
 
/**
 * The client side GUI.
 * @author Risto Novik
 *
 */
public class SocketClient extends JFrame implements Observer {
    /**
	 * Generated serial code.
	 */
	private static final long serialVersionUID = -4337224685051849004L;
	/**
	 * Each client has own player object which is unique.
	 */
	private Player player;
	/**
	 * Object to communicate the server.
	 */
	private NetworkListenerModel listener;
	/**
	 * Instance for logging the game statistics.
	 */
	private GameStatisticsLog stat;
	/**
	 * Text area for the player names in game.
	 */
	private JTextArea namesTextArea = new JTextArea();
	/**
	 * Scroll for text area.
	 */
	private JScrollPane namesScrollPane = new JScrollPane(namesTextArea);
	/**
	 * Status label, where all the messages will appear.
	 */
	private JLabel status = new JLabel("Game start about in 15 seconds");
	/**
	 * Status of much time is left.
	 */
	private JLabel timerStepsLabel = new JLabel(); 
	/**
	 * Roll button.
	 */
	private JButton rollDiceButton;
	/**
	 * Panel where board is drawn.
	 */
	private DrawPanel drawPanel;
	/**
	 * Flag for question asking.
	 */
	private boolean questionAsked = true;
	/**
	 * Timer for the turn, if the player is holding the turn.
	 */
	private Timer timer;
	/**
	 * How many steps must the player wait, before turn changes.
	 */
	private int timerSteps = 60;
	/**
	 * Dialog for questions.
	 */
	private JDialog questionDialog;
	
	/**
	 * Default constructor.
	 */
    public SocketClient() {
        player = new Player(generateName());
		listener = new NetworkListenerModel(this, player);
        stat = GameStatisticsLog.getInstance();
        
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);
        add(getSouthPanel(), BorderLayout.SOUTH);
        
        namesTextArea.setEditable(false);
        namesTextArea.setColumns(10);
        add(namesScrollPane, BorderLayout.EAST);
        add(getRollButton(), BorderLayout.WEST);
        
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				listener.stop();
			}
		});
        // Load icon
		setIconImage(new ImageIcon(
				this.getClass().getResource("/client/images/circus-icon.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Circus game client - " + player.getName());
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(this);
    }
    
    /**
     * Return the roll button with the action listener.
     * @return JButton
     */
    private JButton getRollButton() {
    	rollDiceButton = new JButton(" Roll");
        Font f = new Font("SansSerif", Font.BOLD, 19);
        rollDiceButton.setFont(f);
        rollDiceButton.setIcon(new ImageIcon(
				this.getClass().getResource("/client/images/dice.png")));
        rollDiceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        rollDiceButton.setHorizontalTextPosition(SwingConstants.CENTER);
        rollDiceButton.setEnabled(false);
        rollDiceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDiceButton.setEnabled(false);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						listener.diceRoll(false);
						endTurn();
					}
				});
				
			}
		});
        return rollDiceButton;
    }
    
    /**
     * Panel for the status of game.
     * @return JPanel
     */
    private JPanel getSouthPanel() {
    	JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	north.add(status);
    	
    	JLabel color = new JLabel("ooOOoo");
    	color.setBackground(player.getColor());
    	color.setForeground(player.getColor());
    	color.setBorder(BorderFactory.createLineBorder(Color.black));
    	north.add(color);
    	north.add(timerStepsLabel);
    	return north;
    }
    
    /**
     * Add all players who has registered and connected to JTextarea.
     * @param players ArrayList of players
     * @param currentTurn Player
     */
    private void addCurrentPlayers(ArrayList<Player> players, Player currentTurn) {
    	namesTextArea.setText("");
    	for (Player player : players) {
			namesTextArea.append(player.getName() 
					+  " W(" + player.getWin() + ") " + ((player == currentTurn) ? "*" : "") + "\n");
		}
    }
    
    /**
     * Response from server when the board object has been received.
     * Send the board object to drawing panel.
     * @param o Observable
     * @param arg Object
     */
    public void update(Observable o, final Object arg) {
        SwingUtilities.invokeLater(new Thread() {
            public void run() {
                Board board = (Board) arg;
                if (board != null) {
	                changeStatus(board);
	                drawPanel.setGameBoard(board);
	                drawPanel.repaint();
	                
	                // On end tile
	                if (board.getGameState() == GameState.END) {
	                	// Add the statistic to txt file
	                	stat.log(String.format("Start %s End %s Total turns %d winner %s\n",
	                		board.getStartGame(), new Date(System.currentTimeMillis()), board.getTurns(), 
	                		board.getLastTurn().getWhoseTurn().getName()));
	                }
	                
	                // This client turn
	                if (board.getGameState() == GameState.PLAYING) {
	                	if (board.getCurrentTurn().equals(player)) {
	                		setupTimer();
	                    	rollDiceButton.setEnabled(
	                    			askQuestion(board.getCurrentTurn().getCurrentLocation()));											
	                	}
	   
	                } else {
	                	rollDiceButton.setEnabled(false);
	                }
	            }
            }
        });
    }
    
    /**
     * If the player's on QuestionTile the question will be asked.
     * @param tile Tile
     * @return boolean
     */
    private boolean askQuestion(Tile tile) {
		if (tile instanceof QuestionTile && questionAsked) {
			questionAsked = false;
			QuestionTile currentTile = (QuestionTile) tile;
			QuestionEntry entry = currentTile.getRandomQuestion();
			
			if (questionDialog == null && !createDialog(entry.getQuestion(), entry.getAnswer())) {
				listener.diceRoll(true);
				status.setText("Not corret your turn score is 0, try next time again");
				endTurn();
				return false;
			} else {
				status.setText("Roll quickly again, or there will be new question!");
				questionAsked = true;
				return true;
			}
			
		}
		return true && questionAsked;
    }
    
    /**
     * Create the question dialog.
     * @param message the question itself.
     * @param correctAnswer correct answer as the JOptionPane.YES_OPTION or NO_OPTION
     * @return boolean on correct true and if wrong false.
     */
    private boolean createDialog(String message, int correctAnswer) {
		JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		questionDialog = pane.createDialog(this, "Question to player " + player.getName());
		
		if (!questionDialog.isVisible()) {
			questionDialog.setLocationRelativeTo(this);
			questionDialog.setModal(true);
			questionDialog.setVisible(true);
			
			Object value = pane.getValue(); 
			if (value == null || value instanceof String) {
				return false;
			} else if (((Integer) value).intValue() == correctAnswer) {
				return true;
			}
		}
		return false;
    }
    
    /**
     * Change the text on status bar.
     * @param board Board
     */
    private void changeStatus(Board board) {
    	Turn lastTurn = board.getLastTurn();
    	String msg = "";
    	
    	if (lastTurn != null) {
    		Tile lastLocation = lastTurn.getWhoseTurn().getCurrentLocation();
    		
    		msg = String.format("last turn by %s, rolled %d %s", 
    				lastTurn.getWhoseTurn().getName(), lastTurn.getDiceScore(),
    				lastLocation.getSmallDescription());
    	}
    	
        status.setText(String.format("Total turns %d, %s", board.getTurns(), msg));
        addCurrentPlayers(board.getPlayers(), board.getCurrentTurn());
    }
        
    /**
     * Timer to follow the rule that one player could not hold
     * the all game.
     */
    private void setupTimer() {
    	if (timer == null) {
	    	timer = new Timer();
	    	timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					timerSteps--;
					if (timerSteps <= 0) {
						listener.diceRoll(true);
						endTurn();
					}
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							timerStepsLabel.setForeground(
									(timerSteps <= 10) ? Color.red : Color.black);
							timerStepsLabel.setText(" S: " + timerSteps);
						}
					});
				}
			}, 0, 1000);
    	}
	}
    
    /**
     * End the player turn reset the timers and dialogs.
     */
    private void endTurn() {
		rollDiceButton.setEnabled(false);
		if (questionDialog != null) {
			questionDialog.dispose();
			questionDialog = null;
		}
		
		if (timer != null) {
			timer.cancel();
			timer = null;
			timerSteps = 60;
		}
		
		if (!questionAsked) 
			questionAsked = true;
    }
    
	/**
	 * Generate random name.
	 * @return String player name
	 */
	private String generateName() {
		String[] names = { "Vanja", "Miku", "Kiku", "Juku", "Toots", "Mart", "Igor", "Marju" };
		return names[(int) (Math.random() * names.length)] + (int) (Math.random() * 1000);
	}
	
	/**
	 * Main program to start the client GUI.
	 * @param args String array
	 */
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					new SocketClient().setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							null, "Problem occurd on starting client: \n" + e.getMessage());
				}
			}
		});
    	
    }
 }

