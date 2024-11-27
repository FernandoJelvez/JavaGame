package io.github.gameMakerTool;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;
//Recuerda que para cualquier metodo que manda texto puedes usar override

public class ToolUserInterface {
	private static final Scanner input = new Scanner(System.in);
	private static AbstractExceptionManager exceptionManager;
	private static Tool tool;
	public static void startTool(ExceptionManager exceptionManager,Tool tool){
		ToolUserInterface.exceptionManager=exceptionManager;
		ToolUserInterface.tool=tool;
		System.out.println("Welcome to the GameDev Tool User Interface");
		System.out.println("Please type 'help' for help");
		while(true) {
			System.out.print("> ");
			readCommand(input.nextLine());
		}
	}

	public static void readCommand(String command){
		String[] splitCommand = command.split(" ");
		String[] commandArguments = Arrays.stream(splitCommand).skip(1).toArray(String[]::new);
		try {
			if(command.toCharArray()[0]==' '){
				throw new Exception("Found blank space at the beginning of the command");
			}
			validateCommands(splitCommand);
			if (checkForCommands(splitCommand) && !checkForCommands(commandArguments)) {
				redirectCommand(splitCommand[0], commandArguments);
			}
		} catch (Exception e){
			exceptionManager.catchException(e);
		}
	}

	public static void redirectCommand(String command,String[] arguments){
		try {
			switch (command) {
				case "fill":
					fill(arguments);
					break;
				case "place":
					place(arguments);
					break;
				case "help":
					help();
					break;
			}
		} catch(Exception e) {
			exceptionManager.catchException(e);
		}
	}

	/**
	 * shows help
	 */
	private static void help() {
		System.out.println("""
				Game Tool User Interface| Help
				keyword argumentKeyword arguments
				please replace the spaces between '' with the arguments
				/ indicates that the user must choose only one of the argument keys at its sides
				the coordinates must be registered in units (for a 600px tall screen the unit's value is 10px)
				fill rectangle 'tileName' 'initialX' 'initialY' 'finalX' 'finalY'
				fill stair 'tileName' 'initialX' 'initialY' 'width in tiles' 'west/east'
				place tile/entity 'tileName' 'initialX' 'initialY' 'finalX' 'finalY'
				place entity 'tileName' 'initialX' 'initialY' 'finalX' 'finalY' 'xSpeed' 'ySpeed'""");
	}

	public static void fill(String[] arguments) throws Exception{
		try {
			validateFill(arguments);
			Point startingPoint = new Point(Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]));
			if (arguments[0].equals(FillArgumentKeywords.rectangle.toString())) {
				Point endingPoint = new Point(Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]));
				tool.fillRectangle(arguments[1], startingPoint, endingPoint);
			} else if (arguments[0].equals(FillArgumentKeywords.stair.toString())) {
				int widthInTiles = Integer.parseInt(arguments[4]);
				String orientation = arguments[5];
				tool.fillStair(arguments[1], (int)startingPoint.getX(),(int)startingPoint.getY(), widthInTiles, orientation);
			}
		} catch (NumberFormatException e){
			throw new Exception(e.getLocalizedMessage()+", not an integer number");
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static void place(String[] arguments) throws Exception{
		try {
			validatePlace(arguments);
			int x = Integer.parseInt(arguments[2]);
			int y = Integer.parseInt(arguments[3]);
			if (arguments[0].equals(PlaceArgumentKeywords.tile.toString())) {
				tool.placeTile(arguments[1], new Point(x, y));
			}else if(arguments[0].equals(PlaceArgumentKeywords.entity.toString())) {
				if (arguments.length !=6) {
					tool.placeEntity(arguments[1], new Point(x, y));
				} else {
					int xSpeed = Integer.parseInt(arguments[4]);
					int ySpeed = Integer.parseInt(arguments[5]);
					tool.placeEntity(arguments[1], new Point(x, y), xSpeed, ySpeed);
				}
			}
		} catch (NumberFormatException e){
			throw new Exception(e.getLocalizedMessage()+", not an integer");
		} catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}

	public static boolean checkForCommands(String[] s){
		boolean flag=false;
		for (String string : s) {
			if (Commands.contains(string)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean checkForCommand(String s){
		return Commands.contains(s);
	}
	public static boolean checkForArgument(String s) {
		return FillArgumentKeywords.contains(s) || PlaceArgumentKeywords.contains(s);
	}

	public static void validateCommands(String[] splitCommand) throws Exception{
		int commandsCount=0;
		int argumentsCount=0;
		for (String string:splitCommand) {
			if(checkForCommand(string)){
				commandsCount++;
			}
			if(checkForArgument(string)){
				argumentsCount++;
			}
		}
		if(commandsCount != 1) {
			throw new Exception("Invalid command, commands must have only one command keyword");
		} else if (argumentsCount > 1) {
			throw new Exception("Invalid command, commands must have only up to one argument keyword");
		} else if (argumentsCount <1 && !splitCommand[0].equals("help")) {
			throw new Exception("This command must have one argument keyword");
		}
	}

	public static void validateFill(String[] arguments) throws ArithmeticException,InvalidArgumentKeywordException{
		if (!FillArgumentKeywords.contains(arguments[0])) {
			throw new InvalidArgumentKeywordException("Invalid argument keyword, it must be "+FillArgumentKeywords.rectangle+" or "+FillArgumentKeywords.stair);
		} else if (arguments.length!=6) {
			throw new ArithmeticException("This command must contain 1 argument keyword and 5 arguments");
		}
	}

	public static void validatePlace(String[] arguments) throws ArithmeticException,InvalidArgumentKeywordException {
		if (!PlaceArgumentKeywords.contains(arguments[0])) {
			throw new InvalidArgumentKeywordException("Invalid argument keyword, it must be "+PlaceArgumentKeywords.entity+" or "+PlaceArgumentKeywords.tile);
		}else if(arguments.length!=6&& arguments.length!=4) {
			if(arguments[0].equals(PlaceArgumentKeywords.entity.toString())){
				throw new ArithmeticException("This command must contain 1 argument keyword and 3 or 5 arguments");
			} else {
				throw new ArithmeticException("This command must contain 1 argument keyword and 3 arguments");
			}
		}
	}
}
