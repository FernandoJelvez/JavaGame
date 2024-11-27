package io.github.gameMakerTool;

public enum FillArgumentKeywords {
	stair,
	rectangle;
	public static boolean contains(String s){
		boolean flag=false;
		for (FillArgumentKeywords command: FillArgumentKeywords.values()) {
			if(command.toString().equals(s)){
				flag=true;
				break;
			}
		}
		return flag;
	}

}
