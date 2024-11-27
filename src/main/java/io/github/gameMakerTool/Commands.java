package io.github.gameMakerTool;

public enum Commands {
	help,
	fill,
	place;

	public static boolean contains(String s){
		boolean flag=false;
		for (Commands command:Commands.values()) {
			if(command.toString().equals(s)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
