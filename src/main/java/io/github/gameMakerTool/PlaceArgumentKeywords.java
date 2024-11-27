package io.github.gameMakerTool;

public enum PlaceArgumentKeywords {
	tile,
	entity;
	public static boolean contains(String s){
		boolean flag=false;
		for (PlaceArgumentKeywords command: PlaceArgumentKeywords.values()) {
			if(command.toString().equals(s)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
