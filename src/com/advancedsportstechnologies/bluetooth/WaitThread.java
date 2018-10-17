package com.advancedsportstechnologies.bluetooth;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 * Background thread to wait for Bluetooth connection
 */
public class WaitThread implements Runnable{

	//Unique ID that is required for clients to connect to this device
	private final String SERVER_UUID = "04c6093b00001000800000805f9b34fb";

	/** Default Constructor Required **/
	public WaitThread() {
	}

	/**
	 * When thread starts, wait for a Bluetooth connection
	 */
	@Override
	public void run() {
		waitForConnection();		
	}
	
	/** Waiting for connection from devices */
	private void waitForConnection() {
		// retrieve the local Bluetooth device object
		LocalDevice local;
		
		StreamConnectionNotifier notifier;
		StreamConnection connection;
		
		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			
			UUID uuid = new UUID(SERVER_UUID, false);
			System.out.println("Publishing services at: " + uuid.toString());
			
            String url = "btspp://localhost:" + uuid.toString() + ";name=ScoreboardConfig";
            notifier = (StreamConnectionNotifier)Connector.open(url);
        } catch (BluetoothStateException e) {
        	System.out.println("Bluetooth is not turned on.");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		// wait for connection
		while(true) {
			try {
				System.out.println("Waiting for client connection...");

	            //When connection is established, create new worker thread to process messages
				connection = notifier.acceptAndOpen();
	            Thread processThread = new Thread(new ProcessConnectionThread(connection));
	            processThread.start();
	            
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
