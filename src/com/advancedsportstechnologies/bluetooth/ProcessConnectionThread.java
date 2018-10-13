package com.advancedsportstechnologies.bluetooth;

import com.advancedsportstechnologies.model.Match;

import javax.microedition.io.StreamConnection;
import java.io.InputStream;

public class ProcessConnectionThread implements Runnable{

	private StreamConnection mConnection;
	
	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;
	
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
				int data = inputStream.read();

				if (data == EXIT_CMD)
				{
					System.out.println("finish process");
					break;
				}

				while (data != 124) {
					char c = (char) data;
					result.append(c);
				    data = inputStream.read();
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
		String[] teamNames = result.split("/");
		Match.setTeams(teamNames[0], teamNames[1]);
		Match.start();
	}
}
