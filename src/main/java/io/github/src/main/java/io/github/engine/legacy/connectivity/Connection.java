package io.github.engine.legacy.connectivity;

import java.io.IOException;

public class Connection {
	private Connectable connectable;

	public Connection(String host, int port, Protocol clientProtocol) throws IOException {
		connectable = new Client(host, port,clientProtocol);
	}
	public Connection(int port, Protocol serverProtocol) throws IOException {
		connectable=new Server(port,serverProtocol);
	}

	public void start() throws IOException {
		connectable.startConnection();
	}

}
