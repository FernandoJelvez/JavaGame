package org.example.workpaths.two.game;

import org.example.workpaths.two.engine.Protocol;

import java.io.InputStream;
import java.io.OutputStream;

public class DefaultGameProtocol implements Protocol {
	public DefaultGameProtocol() {
	}

	@Override
	public OutputStream processRequest(InputStream input) {
		return null;
	}

	@Override
	public void initializeCommunication() {

	}
}
