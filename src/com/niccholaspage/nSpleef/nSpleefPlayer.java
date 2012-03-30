package com.niccholaspage.nSpleef;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class nSpleefPlayer {
	private final Player player;
	private int state = 0;
	private boolean ready = false;
	private Location location;
	public nSpleefPlayer(Player player){
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
	public nSpleefPlayer setState(int state){
		this.state = state;
		return this;
	}
	public int getState(){
		return state;
	}
	public nSpleefPlayer setReady(boolean ready){
		this.ready = ready;
		return this;
	}
	public boolean getReady(){
		return ready;
	}
	public nSpleefPlayer setLocation(Location location){
		this.location = location;
		return this;
	}
	public Location getLocation(){
		return location;
	}
}
