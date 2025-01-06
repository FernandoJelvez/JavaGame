package io.github.engine.connectivity.exceptions;

public class WrongProtocolException extends RuntimeException{
	public WrongProtocolException() {
	}

	public WrongProtocolException(String message) {
	}

	public WrongProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongProtocolException(Throwable cause) {
		super(cause);
	}

	public WrongProtocolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
