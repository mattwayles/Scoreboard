package com.advancedsportstechnologies.bluetooth;

import com.advancedsportstechnologies.model.Match;
import javafx.application.Platform;
import org.json.JSONArray;
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
			
			// prepare to receive data
			InputStream inputStream = mConnection.openInputStream();

			System.out.println("waiting for input");
	        
	        while (true) {
				StringBuilder result = new StringBuilder();

				int buffer = inputStream.read();


				if (buffer == EXIT_CMD)
				{
					System.out.println("finish process");
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
				String matchType = resultObj.getString("type");
				int numGames = resultObj.getInt("numGames");
				String team1Name = resultObj.getString("team1");
				String team2Name = resultObj.getString("team2");

                JSONArray gameScores = resultObj.getJSONArray("gameScores");
                int[] scores = new int[gameScores.length()];
                for (int i = 0; i < gameScores.length(); i++) {
                    scores[i] = gameScores.getInt(i);
                }


                //TODO: This should be setting all the match information appropriately. Now I need to make sure there aren't any hard-coded values, and that the Match properties are being used throughout!
				Platform.runLater(() ->
				{
					Match.setType(matchType);
					Match.setMaxGames(numGames);
					Match.setGameScores(scores);
					Match.setTeams(team1Name, team2Name);
					Match.start();
				});

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
