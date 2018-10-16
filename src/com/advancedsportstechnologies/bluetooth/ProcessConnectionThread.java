package com.advancedsportstechnologies.bluetooth;

import com.advancedsportstechnologies.controller.Controller;
import com.advancedsportstechnologies.model.Match;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.io.StreamConnection;
import java.io.InputStream;

public class ProcessConnectionThread implements Runnable{

	private StreamConnection mConnection;
	
	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	
	ProcessConnectionThread(StreamConnection connection)
	{
		mConnection = connection;
	}
	
	@Override
	public void run() {
		try {

			//Inform UI of connection
			Match.setConnected(true);
			Platform.runLater(Match::startOrRefresh);

			// prepare to receive data
			InputStream inputStream = mConnection.openInputStream();

			System.out.println("waiting for input");
	        
	        while (true) {
				StringBuilder result = new StringBuilder();

				int buffer = inputStream.read();


				if (buffer == EXIT_CMD)
				{
					System.out.println("finish process");
					Match.setConnected(false);
					Platform.runLater(Match::startOrRefresh);
					break;
				}


				while (buffer > 0) {
					char c = (char) inputStream.read();
					result.append(c);
					buffer--;
				}

	        	processResult(result.toString());
        	}
        } catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Process the result from client
	 * @param result the result string
	 */
	private void processResult(String result) {

		if (result.startsWith("{") || result.startsWith("[")) {
			JSONObject resultObj;
			try {
				resultObj = new JSONObject(result);
				int numGames = resultObj.getInt("numGames");
				int gamesToWin = resultObj.getInt("gamesToWin");
				String matchType = resultObj.getString("type");
				String team1Name = resultObj.getString("team1");
				String team2Name = resultObj.getString("team2");

				String gameScoreStr = resultObj.getString("gameScores");
				gameScoreStr = gameScoreStr.replace("[", "").replace("]","");
				String[] gameScores = gameScoreStr.split(",");

                int[] scores = new int[gameScores.length];
                for (int i = 0; i < gameScores.length; i++) {
                    scores[i] = Integer.parseInt(gameScores[i].trim());
                }

				Platform.runLater(() ->
				{
					Match.setType(matchType);
					Match.setGamesToWin(gamesToWin);
					Match.setMaxGames(numGames);
					Match.setGameScores(scores);
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
