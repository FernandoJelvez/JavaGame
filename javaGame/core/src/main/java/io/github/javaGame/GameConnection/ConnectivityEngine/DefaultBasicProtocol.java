package org.example.workpaths.two.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultBasicProtocol implements Protocol {
	private String host;
	private String port;
	public DefaultBasicProtocol(){

	}

	@Override
	public OutputStream processRequest(InputStream input) {
		return null;
	}

	@Override
	public void initializeCommunication() {

	}
}
