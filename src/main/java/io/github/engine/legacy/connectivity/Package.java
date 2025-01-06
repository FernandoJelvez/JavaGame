package io.github.engine.legacy.connectivity;

import java.io.Serializable;
import java.time.Instant;

public class Package implements Serializable {
	private final String message;
	private final Instant instant;

	public Package(String message, Instant instant) {
		this.message = message;
		this.instant = instant;
	}

	public Instant getInstant() {
		return instant;
	}

	public String getMessage() {
		return message;
	}
}
