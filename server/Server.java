package server;
// Usage:

//        java Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.

import java.net.*;
import java.util.HashMap;
import common.Port;
import common.Report;

import java.io.*;

public class Server {

	public static void main(String[] args) {

		// This table will be shared by the server threads:
		ClientTable clientTable = new ClientTable();
		HashMap<String, ServerThreadHandler> threadMap = new HashMap<String, ServerThreadHandler>();

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(Port.number);
		} catch (IOException e) {
			Report.errorAndGiveUp("Couldn't listen on port " + Port.number);
		}

		Report.behaviour("Server is running!");
		try {
			// We loop for ever, as servers usually do.
			while (true) {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = serverSocket.accept(); // Matches AAAAA in Client

				// This is so that we can use readLine():
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// We ask the client what its name is:
				String clientName = fromClient.readLine(); // Matches BBBBB in Client

				Report.behaviour(clientName + " connected");

				// We add the client to the table:
				clientTable.add(clientName);
				clientTable.getQueue(clientName).clear();

				ServerThreadHandler handler = new ServerThreadHandler(clientName, socket, clientTable);
				threadMap.put(clientName, handler);
				handler.start();

				// We create and start a new thread to read from the client:
				// (new ServerReceiver(clientName, fromClient, clientTable)).start();

				// We create and start a new thread to write to the client:
				// PrintStream toClient = new PrintStream(socket.getOutputStream());
				// (new ServerSender(clientTable.getQueue(clientName), toClient)).start();
			}
		} catch (IOException e) {
			// Lazy approach:
			Report.error("IO error " + e.getMessage());
			// A more sophisticated approach could try to establish a new
			// connection. But this is beyond the scope of this simple exercise.
		}
	}
}
