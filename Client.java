


import java.io.*;
import java.net.*;

class Client {

  public static void main(String[] args) {

    // Check correct usage:
    if (args.length != 2) {
      Report.errorAndGiveUp("Usage: java Client user-nickname server-hostname");
    }

    // Initialize information:
    String nickname = args[0];
    String hostname = args[1];

    if (nickname.equals("quit")) {
    	Report.errorAndGiveUp("Username cannot be \"quit\" ");
    }
    // Open sockets:
    PrintStream toServer = null;
    BufferedReader fromServer = null;
    Socket server = null;

    try {
      server = new Socket(hostname, Port.number); // Matches AAAAA in Server.java (
      fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
      toServer = new PrintStream(server.getOutputStream());
    } 
    catch (UnknownHostException e) {
      Report.errorAndGiveUp("Unknown host: " + hostname);
    } 
    catch (IOException e) {
      Report.errorAndGiveUp("The server doesn't seem to be running " + e.getMessage());
    }

    // Tell the server what my nickname is:
    toServer.println(nickname); // Matches BBBBB in Server.java
     
    // Create two client threads of a different nature:
    ClientReceiver receiver = new ClientReceiver(fromServer);
    ClientSender sender = new ClientSender(nickname, toServer, receiver);

    // Wait for them to end and close sockets.
    try {
      sender.join();
      toServer.close();
      receiver.join();
      fromServer.close();
      server.close();
    }
    catch (IOException e) {
      Report.errorAndGiveUp("Something wrong " + e.getMessage());
    }
    catch (InterruptedException e) {
      Report.errorAndGiveUp("Unexpected interruption " + e.getMessage());
    }
  }
}
