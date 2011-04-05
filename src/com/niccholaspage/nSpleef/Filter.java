package com.niccholaspage.nSpleef;

public class Filter {
	public static nSpleef plugin;
	public static void init (nSpleef instance){
		plugin = instance;
	}
	public static nSpleefArena getArenaByName(String name){
		for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			if (plugin.nSpleefArenas.get(i).getName().equalsIgnoreCase(name)){
				return plugin.nSpleefArenas.get(i);
			}
		}
		return null;
	}
	public static nSpleefArena getArenaByGame(String game){
		for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			if (plugin.nSpleefArenas.get(i).getName().equalsIgnoreCase(game.split(",")[1])){
				return plugin.nSpleefArenas.get(i);
			}
		}
		return null;
	}
	public static Integer getArenaIndex(nSpleefArena arena){
		for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			if (plugin.nSpleefArenas.get(i).equals(arena)){
				return i;
			}
		}
		return null;
	}
	public static String getGameByName(String name){
		for (int i = 0; i < plugin.nSpleefGames.size(); i++){
			if (plugin.nSpleefGames.get(i).split(",")[0].equalsIgnoreCase(name)){
				return plugin.nSpleefGames.get(i);
			}
		}
		return null;
	}
}
