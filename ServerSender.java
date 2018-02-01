import java.net.*;
import java.io.*;
import java.util.concurrent.*;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
  private BlockingQueue<Message> clientQueue;
  private PrintStream client;

  public ServerSender(BlockingQueue<Message> q, PrintStream c) {
    clientQueue = q;   
    client = c;
  }

  public void run() {
    while (true) {
      try {
        Message msg = clientQueue.take(); // Matches EEEEE in ServerReceiver
        client.println(msg); // Matches FFFFF in ClientReceiver
      }
      catch (InterruptedException e) {
        // Do nothing and go back to the infinite while loop.
      }
    }
  }
}

/*

 * Throws InterruptedException if interrupted while waiting

 * See https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html#take--

 */
