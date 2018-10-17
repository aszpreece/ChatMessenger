# Simple messaging system

  * Based on the client-server architecture with server threads and
    socket communication.

# My Solution
  * Created two new handler classes that manage the running of the sender and reciever threads for the client and server respectively
  * Add simple if statement along with error message to prevent a client using the nickname 'quit'
  * Upon a user sending "quit" to the server, the user sender thread calls its handler's close method, which interrupts the sender and causes it to stop. To get the blocking method readline in teh reciever to exit, the server socket must also be closed to make it return null.
  * Modifying the server was easier as the code for sending is must simpler and there are no annoying blocking methods.
  * The user's are removed from the cleint table as they disconnect.

# Specification (From lecturer)

  * Implement a simple messaging system, based on the client-server
    architecture, using threads to serve the clients.

  * Races and deadlocks should be avoided.

The server should be run as 

  $ java Server

The clients should be run as 

  $ java user-name server-address

If there already is a user with this nickname, in this simpled minded
design, the other user becomes innaccessible.

Once my client is running, I can send a message to John by writing
"John" and "Hello" in separate line. So the first line is the adressee
and the second line is the message. That's all we can do, again and
again, in this simpled minded design. There is no provision for the
client to end.

## Report.java

   * A simple class for reporting normal behaviour and erroneous behaviour.

   * Its methods are all static, and we don't create objects of this class.

## Port.java

   * A class with a static variable defining the socket port, shared by the client and server.
  
## Message.java

   * Used by the server.
   * A message has the sender name and a text body.

## ClientTable.java

   * Used by the server.
   * It associates a message blocking queue to each client name.
   * Implemented with Map.
   * More precisely with the interface ConcurrentMap using the implementation ConcurrentHashMap.

## Client.java

   * Reads user name and server address from command line.
   * Opens a socket for communication with the server.
   * Sends the user name to the server.
   * Starts two threads ClientSender and ClientReceiver.
   * Waits for them to end.
   * Then it itself ends.

## ClientSender.java

   * Loops forever doing the following.
   * Reads a recipient name from the user.
   * Reads a message from the user.
   * Sends them both to the server.

## ClientReceiver.java

   * Loops forever doing the following.
   * Reads a string from the server.
   * Prints it for the user to see.

## Server.java

   * Creates server socket.
   * Creates a client table as explained above.
   * Loops forever doing the following.
   * Waits for connection from the socket.
   * Reads the client name.
   * Updates the table with the client as the key and a new queue for it as the value.
   * Starts two threads ServerReceiver and ServerSender.
   
## ServerReceiver.java

   * Loops for ever doing the following.
   * Reads two strings from the client. One it the recipients name. The other is the message.
   * Puts the message in the queue for the recipient.
   * Uses the table to find the queue.

## ServerSender.java

   * Loops for ever reading a message from queue for its correponding
     client (ClientReceiver), and seding it to the client. The table
     is not needed, because the server sender handles one specific
     client.

