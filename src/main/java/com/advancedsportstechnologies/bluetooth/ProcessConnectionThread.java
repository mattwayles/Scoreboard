package com.advancedsportstechnologies.bluetooth;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.model.Match;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import javax.microedition.io.StreamConnection;
import java.io.InputStream;

/**
 * Bluetooth class to wait for and process messages sent from clients
 */
public class ProcessConnectionThread implements Runnable{

	private StreamConnection mConnection;
	
	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;

    /**
     * Create a ProcessConnection thread with the current bluetooth connection
     * @param connection    The current bluetooth connection
     */
	ProcessConnectionThread(StreamConnection connection)
	{
		mConnection = connection;
	}

    /**
     * Start the background worker thread that will wait for and process client messages
     */
	@Override
	public void run() {
		try {

			//Inform UI of connection
			Match.setConnected(true);
			if (Match.isActive()) {
				Platform.runLater(Match::startOrRefresh);
			}

			// prepare to receive data
			InputStream inputStream = mConnection.openInputStream();

			System.out.println("Connected to client. Awaiting Input");
	        
			//Thread will sit in this loop until the socket is closed
	        while (true) {
	            //Create a container for the message
				StringBuilder message = new StringBuilder();

				//Read the first message, which will always be a buffer int
				int buffer = inputStream.read();
				
				//If buffer is -1, socket is closed
				if (buffer == EXIT_CMD)
				{
					mConnection.close();
					System.out.println("Socket closed, safe to disconnect");
					Match.setConnected(false);
					
					//Refresh UI by removing Bluetooth icon
					if (Match.isActive()) {
						Platform.runLater(Match::startOrRefresh);
					}
					break;
				}
				
				//After each buffer, the actual message is sent
				while (buffer > 0) {
					char c = (char) inputStream.read();
					message.append(c);
					buffer--;
				}

				//Process the message
	        	processResult(message.toString());
        	}
        } catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Process the message from client
	 * @param message the message string
	 */
	private void processResult(String message) {

	    //Ensure client message is JSON format
		if (message.startsWith("{") || message.startsWith("[")) {
			JSONObject messageObj;
			try {

			    //Parse the JSON string into an object
				messageObj = new JSONObject(message);
				int numGames = messageObj.getInt("numGames");
				int gamesToWin = messageObj.getInt("gamesToWin");
				boolean winByTwo = messageObj.getBoolean("winByTwo");
				String matchType = messageObj.getString("type");
				String matchTheme = messageObj.getString("theme");
				String team1Name = messageObj.getString("team1");
				String team2Name = messageObj.getString("team2");

				//Format the gameScores string xx-xx-xx... into an array
				String gameScoreStr = messageObj.getString("gameScores");
				String[] gameScores = gameScoreStr.split("-");
                int[] scores = new int[gameScores.length];
                for (int i = 0; i < gameScores.length; i++) {
                    scores[i] = Integer.parseInt(gameScores[i].trim());
                }

                //Start a new match with the settings sent in message
				Platform.runLater(() ->
				{
					Match.setTheme(matchTheme);
					Match.setType(matchType);
					Match.setGamesToWin(gamesToWin);
					Match.setMaxGames(numGames);
					Match.setGameScores(scores);
					Match.setWinByTwo(winByTwo);
					Match.setTeams(team1Name, team2Name);

					Controller.restartScoreboard();
					Match.startOrRefresh();
				});

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
