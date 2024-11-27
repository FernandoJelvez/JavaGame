package io.github.gameMakerTool;

public class InvalidArgumentKeywordException extends Exception {
	public InvalidArgumentKeywordException() {
		super("Tile not found");
	}

	public InvalidArgumentKeywordException(String message) {
		super(message);
	}
}
