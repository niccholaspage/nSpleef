package com.niccholaspage.nSpleef.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleefArena;

public class Session {
	private final Player player;
	
	private nSpleefArena arena = null;
	
	private Location block1;
	
	private Location block2;
	
	public Session(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setBlock1(Location block1){
		this.block1 = block1;
	}
	
	public Location getBlock1(){
		return block1;
	}
	
	public void setBlock2(Location block2){
		this.block2 = block2;
	}
	
	public Location getBlock2(){
		return block2;
	}
	
	public nSpleefArena getArena(){
		return arena;
	}
	
	public void setArena(nSpleefArena arena){
		this.arena = arena;
	}
}