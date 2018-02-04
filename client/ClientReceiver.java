package client;

import java.io.*;

import common.Report;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class ClientReceiver extends Thread {

	private BufferedReader server;
	private String nickname;
	private ClientThreadHandler handler;

	ClientReceiver(String nickname, BufferedReader server, ClientThreadHandler clientThreadHandler) {
		this.server = server;
		this.nickname = nickname;
		this.handler = clientThreadHandler;

	}

	public void run() {
		// Print to the user whatever we get from the server:
		try {
			String s;
			while (!Thread.currentThread().isInterrupted()) {
				// Matches FFFFF in ServerSender.java
				s = server.readLine();
				if (s == null) {
					Report.error("Server seems to have died ");
					break;
				}
				System.out.println(s);
			}
		} catch (IOException e) {
			if (!Thread.currentThread().isInterrupted())
				Report.error("Server seems to have died " + e.getMessage());
		}
		handler.close();

	}
}

/*
 * 
 * The method readLine returns null at the end of the stream
 * 
 * It may throw IoException if an I/O error occurs
 * 
 * See https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html#
 * readLine--
 * 
 * 
 */
