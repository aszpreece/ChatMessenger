package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

import common.Report;

public class ServerThreadHandler {
	
	ServerSender sender;
	ServerReceiver receiver;
	private String nickname;
	private BufferedReader fromClient;
	private PrintStream toClient;
	private Socket client;
	ClientTable table;
	

	public ServerThreadHandler(String nickname, Socket client, ClientTable t) {
		this.nickname = nickname;
		try {
			this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.toClient = new PrintStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.client = client;
		receiver = new ServerReceiver(nickname, fromClient, t, this);
		sender = new ServerSender(t.getQueue(nickname), toClient, this);
		table = t;
	}

	public void close() {
		Report.behaviour(nickname + " is disconnecting, shutting down threads...");
		table.getQueue(nickname).clear();
		sender.interrupt();
		receiver.interrupt();
	}

	public void start() {
		receiver.start();
		sender.start();
	}

	public ServerReceiver getReciever() {
		return receiver;
	}

	public ServerSender getSender() {
		return sender;
	}

	public void waitForQuit() throws InterruptedException {
		sender.join();
		Report.behaviour("Sender thread has exited");
		receiver.join();
		Report.behaviour("Reciever thread has exited");
		
	}

}
