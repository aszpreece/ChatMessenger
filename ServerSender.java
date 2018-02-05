

import java.io.*;
import java.util.concurrent.*;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
	private BlockingQueue<Message> clientQueue;
	private PrintStream client;
	ServerThreadHandler handler;

	public ServerSender(BlockingQueue<Message> q, PrintStream c, ServerThreadHandler h) {
		clientQueue = q;
		client = c;
		handler = h;

	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				if (!clientQueue.isEmpty()) {
					Message msg = clientQueue.take(); // Matches EEEEE in ServerReceiver
					client.println(msg); // Matches FFFFF in ClientReceiver
				}
			} catch (InterruptedException e) {
				// Do nothing and go back to the infinite while loop.
			}
		}
		Report.behaviour("Sender thread has exited");
	}
}

/*
 * 
 * Throws InterruptedException if interrupted while waiting
 * 
 * See
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.
 * html#take--
 * 
 */
