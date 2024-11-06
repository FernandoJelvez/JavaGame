package org.example.workpaths.two.engine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public interface Protocol {
	OutputStream processRequest(InputStream input);
	void initializeCommunication();
}
