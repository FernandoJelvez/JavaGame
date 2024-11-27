package io.github.gameMakerTool;

public class ExceptionManager extends AbstractExceptionManager{
	@Override
	public void catchException(Exception e) {
		System.out.println("Error:/ "+e.getMessage()+".");
	}
}
