

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThreadHandler {

	ClientReceiver reciever;
	ClientSender sender;
	String nickname;
	BufferedReader fromServer;
	PrintStream toServer;
	Socket server;

	public ClientThreadHandler(String nickname, BufferedReader fromServer, PrintStream toServer, Socket server) {
		this.nickname = nickname;
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.server = server;
		reciever = new ClientReceiver(nickname, fromServer, this);
		sender = new ClientSender(nickname, toServer, this);
	}

	public void close() {
		// 1. interrupt sender so it can notify server of disconnect
		// 2. interrupt the reciever
		// 3. close down the server connection, unblocking the readline method in the
		// reciever and making it return null.
	
		sender.interrupt();
		reciever.interrupt();
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() {
		reciever.start();
		sender.start();
	}

	public ClientReceiver getReciever() {
		return reciever;
	}

	public ClientSender getSender() {
		return sender;
	}

	public void waitForQuit() throws InterruptedException {

		sender.join();
		Report.behaviour("Sender thread has exited");
		reciever.join();
		Report.behaviour("Reciever thread has exited");

	}

}
