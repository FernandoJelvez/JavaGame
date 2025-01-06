package io.github.engine.legacy.connectivity;

import java.io.IOException;
import java.io.ObjectInputStream;

public interface Protocol {
	/**
	 * General method for the protocol interface, it is supposed to process a request made to a socket and return
	 * the right answer to be sent back
	 * @param input an {@code InputStream}
	 * @return an {@code OutputStream}
	 */
	Package processRequest(ObjectInputStream input) throws IOException, ClassNotFoundException;
}