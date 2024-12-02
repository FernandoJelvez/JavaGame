package io.github.levelMaker;

public class ExceptionManager extends AbstractExceptionManager{
	@Override
	public void catchException(Exception e) {
		System.out.println("Error:/ "+e.getMessage()+".");
	}
}
