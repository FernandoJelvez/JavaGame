package io.github.engine.connectivity;

import io.github.engine.connectivity.exceptions.ConnectionInterruptedException;
import io.github.engine.connectivity.exceptions.WrongProtocolException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.SocketException;

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
		try {
			while(true) {
				Package p = protocol.processRequest(input);
				output.writeObject(p);
			}
		} catch (SocketException e){
			System.out.println("no");
			throw new ConnectionInterruptedException(e);
		} catch (IOException e) {
			throw new RuntimeException();
		} catch (ClassNotFoundException e){
			throw new WrongProtocolException("e");
		}
	}

	@Override
	public void startConnection() {
		try {
			serverSocket.setSoTimeout(0);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		Server server = this;
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}
