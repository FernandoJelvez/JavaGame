package io.github.gameMakerTool;

public class NotSuchTileException extends Exception{
	public NotSuchTileException() {
		super("Tile not found");
	}

	public NotSuchTileException(String message) {
		super(message);
	}
}
