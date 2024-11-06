package org.example.workpaths.two.game;

import org.example.workpaths.two.engine.Client;
import org.example.workpaths.two.engine.Protocol;
import org.example.workpaths.two.engine.Server;

public class Connection {
	private final String host;
	private final int port;
	private final boolean serverMode;
	Server server;
	Client client;
	Protocol protocol;
	public Connection(String host, int port, boolean serverMode, Protocol protocol){
		this.host= host;
		this.port=port;
		this.serverMode=serverMode;
		this.protocol=protocol;
	}
	public void start() throws Exception{
		if (serverMode){
			server=new Server(port,protocol);
		} else {
			client=new Client(host,port,protocol);
		}
	}
}
