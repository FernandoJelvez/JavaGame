package io.github.engine.connectivity.exceptions;

public class ConnectionInterruptedException extends RuntimeException{
	public ConnectionInterruptedException() {
	}

	public ConnectionInterruptedException(String message) {
		super(message);
	}

	public ConnectionInterruptedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionInterruptedException(Throwable cause) {
		super(cause);
	}

	public ConnectionInterruptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
