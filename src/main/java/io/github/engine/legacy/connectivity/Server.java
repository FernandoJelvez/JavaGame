package io.github.engine.legacy.connectivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class Server extends ServerSocket implements Connectable, Runnable {
	private Protocol protocol;
	Client client;
	ServerSocket serverSocket;
	public Server(int port, Protocol serverProtocol) throws IOException {
		serverSocket = new ServerSocket(port);
		protocol=serverProtocol;
	}
	protected void setProtocol(Protocol protocol){
		this.protocol=protocol;
	}

	@Override
	public void run() {
		ObjectInputStream input;
		ObjectOutputStream output;
		try {
			client = new Client(serverSocket.accept());
			input = new ObjectInputStream(client.getInputStream());
			output = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		while(true) {
			try {
				Package p = protocol.processRequest(input);
				output.writeObject(p);
				System.out.println("activeSocket");
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void startConnection() {
		try {
			serverSocket.setSoTimeout(0);
		} catch (Exception e){
			System.out.println(e.getCause().toString());
		}
		Server server = this;
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}
