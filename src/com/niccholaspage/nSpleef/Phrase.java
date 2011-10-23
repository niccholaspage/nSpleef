package com.niccholaspage.nSpleef;

public enum Phrase {
	FIRST_POINT_SELECTED("First point selected."),
	FIRST_POINT_NOT_SELECTED("You haven't chosen your first point."),
	SECOND_POINT_SELECTED("Second point selected."),
	SECOND_POINT_NOT_SELECTED("You haven't chosen your first point."),
	MULTIWORLD_POINT_FAIL("The two selected blocks are in different worlds."),
	NULL_COMMAND("That command doesn't exist!"),
	THIS_IS_NOT_A_CONSOLE_COMMAND("You must be a player to use that command!"),
	GAME_CREATED("The game '$0' has been created."),
	GAME_ALREADY_EXISTS("That game already exists."),
	GAME_DOES_NOT_EXIST("That game doesn't exist."),
	GAME_DELETED("The game '$0' has been deleted."),
	ALREADY_IN_GAME("You are already in a game."),
	GAME_IN_PROGRESS("That game is in progress!"),
	GAME_LIST("Games:"),
	ARENA_ALREADY_EXISTS("That arena already exists."),
	ARENA_DOES_NOT_EXIST("That arena doesn't exist."),
	ARENA_CREATE_FAIL("Could not create the arena!"),
	ARENA_CREATED("The arena '$0' has been created."),
	ARENA_ALREADY_HAS_GAME("That arena already has a game!"),
	ARENA_DELETED("The arena '$0' has been deleted."),
	ARENA_LIST("Arenas:"),
	LISTING("$0 in arena $1"),
	HELP_PAGE("Commands: (Page $0)");
	
	private String message;
	
	private Phrase(String message){
		this.message = message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	private String getMessage(){
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
