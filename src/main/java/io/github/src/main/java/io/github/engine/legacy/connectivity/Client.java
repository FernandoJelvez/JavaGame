package io.github.engine.legacy.connectivity;

import java.io.*;
import java.net.Socket;
import java.time.Instant;

public class Client implements Connectable,Runnable{
	private Protocol protocol;
	private Socket socket;
	ObjectOutputStream serverOutput;
	ObjectInputStream input;
	protected Client(Socket socket){
		this.socket=socket;
	}

	public Client(String host, int port, Protocol clientProtocol) throws IOException {
		socket=new Socket(host, port);
		protocol=clientProtocol;
	}

	protected void setProtocol(Protocol protocol){
		this.protocol=protocol;
	}

	public OutputStream getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}

	@Override
	public void run() {
		try {
			serverOutput.writeObject(new Package("Handshake",Instant.now()));
			while(true) {
				serverOutput.writeObject(protocol.processRequest(input));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startConnection() throws IOException {
		socket.setSoTimeout(0);
		serverOutput=new ObjectOutputStream(getOutputStream());
		input = new ObjectInputStream(getInputStream());
		Client client=this;
		Thread clientThread=new Thread(client);
		clientThread.start();
	}
}
