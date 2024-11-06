package org.example.workpaths.two.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class Server extends ServerSocket implements Runnable {
	Protocol protocol;
	private DataOutputStream output;
	private DataInputStream input;
	private Client client;
	public Server(int port, Protocol protocol) throws IOException {
		super(port);
		this.protocol = protocol;
	}
	public void startServer() throws IOException {
		try{
			client=(Client)accept();
			run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		while (!this.isClosed()){
			try {
				input = (DataInputStream) client.getInputStream();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if(input!=null) {
				output = (DataOutputStream)protocol.processRequest(input);
			}
		}
	}
}
