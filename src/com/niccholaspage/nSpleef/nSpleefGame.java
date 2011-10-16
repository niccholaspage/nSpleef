package com.niccholaspage.nSpleef;

import com.niccholaspage.nSpleef.arena.nSpleefArena;

public class nSpleefGame {
	private final String name;
	
	private final nSpleefArena arena;
	
	public nSpleefGame(String name, nSpleefArena arena){
		this.name = name;
		
		this.arena = arena;
	}
	
	public String getName(){
		return name;
	}
	
	public nSpleefArena getArena(){
		return arena;
	}
	
	public String toString(){
		return name + ":" + arena;
	}
}
