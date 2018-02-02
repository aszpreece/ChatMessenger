
public class ClientThreadHandler {
	
	ClientReceiver reciever;
	ClientSender sender;
	
	public ClientThreadHandler(ClientReceiver r, ClientSender s) {
		reciever = r;
		sender = s;	
	}
	
	public void close() {
		reciever.interrupt();
		sender.interrupt();
	}

}
