package client.base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for writing the information about the game into file.
 * @author Risto Novik
 *
 */
public class GameStatisticsLog {

	/**
	 * Buffer to write into the file.
	 */
	private BufferedWriter out;
	/**
	 * File name with path where statistics is written.
	 */
	private String file;
	/**
	 * Current class instance.
	 */
	private static GameStatisticsLog instance = null;
	
	/**
	 * Singleton pattern to get single object in game.
	 * @return {@link GameStatisticsLog}
	 */
	public static GameStatisticsLog getInstance() {
		if (instance == null) {
			instance = new GameStatisticsLog();
		}
		return instance;
	}
	/**
	 * Set the file path to home, if not found use the default path.
	 * @throws IOException
	 */
	public GameStatisticsLog() {
		this.file = System.getProperty("user.home", ".") 
			+ System.getProperty("file.separator") + "circus.txt";
	}
	
	/**
	 * Append the message to file and close.
	 * @param message String
	 */
	public void log(String message) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(message);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// Player does not need to know about the log writing failure.
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// Closing failed, suppose the file is never been opened.
				}
		}
	}

}
