# Solution
  * Created two new handler classes that manage the running of the sender and reciever threads for the client and server respectively
  * Add simple if statement along with error message to prevent a client using the nickname 'quit'
  * Upon a user sending "quit" to the server, the user sender thread calls its handler's close method, which interrupts the sender and causes it to stop. To get the blocking method readline in teh reciever to exit, the server socket must also be closed to make it return null.
  * Modifying the server was easier as the code for sending is must simpler and there are no annoying blocking methods.
  * The user's are removed from the cleint table as they disconnect.