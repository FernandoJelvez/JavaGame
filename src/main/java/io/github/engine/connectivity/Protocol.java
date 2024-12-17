package io.github.engine.connectivity;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public interface Protocol {
	/**
	 * General method for the protocol interface, it is supposed to process a request made to a socket and return
	 * the right answer to be sent back
	 * @param input an {@code InputStream}
	 * @return an {@code OutputStream}
	 */
	ObjectOutputStream processRequest(ObjectInputStream input);
}