package com.advancedsportstechnologies.bluetooth;

import com.advancedsportstechnologies.model.Match;
import com.advancedsportstechnologies.view.TeamView;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.io.StreamConnection;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProcessConnectionThread implements Runnable{

	private StreamConnection mConnection;
	
	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	
	public ProcessConnectionThread(StreamConnection connection)
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
				String team1Name = resultObj.getString("team1");
				String team2Name = resultObj.getString("team2");
				Platform.runLater(() ->
				{
					TeamView.resetCount();
					Match.setType(matchType);
					Match.setTeams(team1Name, team2Name);
					Match.start();
				});

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
