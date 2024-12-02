package io.github.game;

import io.github.engine.connectivity.WrongProtocolException;
import io.github.engine.connectivity.Protocol;

import java.io.*;

public class GameSocketProtocol implements Protocol {

	@Override
	public OutputStream processRequest(InputStream input){
		return null;
	}
}
