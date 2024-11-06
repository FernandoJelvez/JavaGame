package org.example.workpaths.two.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client extends Socket implements Runnable{
	private String host;
	private int port;
	private Protocol protocol;
	private DataOutputStream output;
	private DataInputStream input;
	public Client(String host, int port, Protocol protocol) {
		super()
	}
	public void startClient(){
		run();
	}

	@Override
	public void run() {
		while (this.isConnected()){

		};
	}
}
