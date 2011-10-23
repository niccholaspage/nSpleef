package com.niccholaspage.nSpleef;

public enum Phrase {
	FIRST_POINT_SELECTED("First point selected."),
	SECOND_POINT_SELECTED("Second point selected."),
	NULL_COMMAND("That command doesn't exist!"),
	THIS_IS_NOT_A_CONSOLE_COMMAND("You must be a player to use that command!"),
	GAME_CREATED("The game $0 has been created."),
	GAME_ALREADY_EXISTS("That game already exists."),
	ARENA_DOES_NOT_EXIST("That arena doesn't exist!"),
	ARENA_ALREADY_HAS_GAME("That arena already has a game!");
	
	private String message;
	
	private Phrase(String message){
		this.message = message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String parse(String... params){
		String parsedMessage = getMessage();
		
		if (params != null){
			for (int i = 0; i < params.length; i++){
				parsedMessage = parsedMessage.replace("$" + (i + 1), params[i]);
			}
		}
		
		return parsedMessage;
	}
}
