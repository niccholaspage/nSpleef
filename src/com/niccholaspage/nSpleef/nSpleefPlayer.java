package com.niccholaspage.nSpleef;

import org.bukkit.entity.Player;

public class nSpleefPlayer {
	private final Player player;
	private Boolean ready = false;
	  public nSpleefPlayer(Player player){
	    this.player = player;
	  }
	  public Boolean getReady(){
	    return this.ready;
	  }
	  public void setReady(Boolean ready){
		  this.ready = ready;
	  }
	  public Player getPlayer(){
		  return this.player;
	  }
}
