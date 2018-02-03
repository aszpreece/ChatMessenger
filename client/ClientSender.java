package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import common.Report;

// Repeatedly reads recipient's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).

public class ClientSender extends Thread {

	private String nickname;
	private PrintStream server;

	ClientThreadHandler handler;

	ClientSender(String nickname, PrintStream server, ClientThreadHandler handler) {
		this.nickname = nickname;
		this.server = server;
		this.handler = handler;
	}

	public void run() {
		// So that we can use the method readLine:
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Then loop forever sending messages to recipients via the server:
			while (!Thread.currentThread().isInterrupted()) {
				String recipient = user.readLine();
				if (recipient.equals("quit")) {
					Report.behaviour("Client is exiting...");
					server.println(-1);
					handler.close();
				} else {
					String text = user.readLine();
					server.println(recipient); // Matches CCCCC in ServerReceiver
					server.println(text); // Matches DDDDD in ServerReceiver
				}
			}

		} catch (IOException e) {
			Report.errorAndGiveUp("Communication broke in ClientSender" + e.getMessage());
		}

		try {
			user.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

/*
 * 
 * What happens if recipient is null? Then, according to the Java documentation,
 * println will send the string "null" (not the same as null!). So maye we
 * should check for that case! Paticularly in extensions of this system.
 * 
 */
