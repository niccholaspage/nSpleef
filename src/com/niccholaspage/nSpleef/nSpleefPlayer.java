package com.niccholaspage.nSpleef;

import org.bukkit.entity.Player;

public class nSpleefPlayer {
	private final Player player;
	private final String arena;
	  public nSpleefPlayer(Player player, String arena){
	    this.player = player;
	    this.arena = arena;
	  }
	  public String getArena(String string){
	    return this.arena;
	  }
	  public Player getPlayer(){
		  return this.player;
	  }
}
