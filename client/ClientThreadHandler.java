package client;

import java.io.BufferedReader;
import java.io.PrintStream;

import common.Report;

public class ClientThreadHandler {

	ClientReceiver reciever;
	ClientSender sender;
	String nickname;
	BufferedReader fromServer;
	PrintStream toServer;

	public ClientThreadHandler(String nickname, BufferedReader fromServer, PrintStream toServer) {
		this.nickname = nickname;
		this.fromServer = fromServer;
		this.toServer = toServer;
		reciever = new ClientReceiver(nickname, fromServer, this);
		sender = new ClientSender(nickname, toServer, this);
	}

	public void close() {
		
		reciever.interrupt();
		sender.interrupt();
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
