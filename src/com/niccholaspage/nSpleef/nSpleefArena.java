package com.niccholaspage.nSpleef;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.niccholaspage.nSpleef.player.Session;

public class nSpleefArena {
	private final String name;
	
	private final World world;
	
	private final Location block1;
	
	private final Location block2;
	
	private final Set<Session> players;
	
	public nSpleefArena(String name, World world, Location block1, Location block2){
		this.name = name;
		
		this.world = world;
		
		this.block1 = block1;
		
		this.block2 = block2;
		
		this.players = new HashSet<Session>();
	}
	
	public String getName(){
		return name;
	}
	
	public World getWorld(){
		return world;
	}
	
	public Location getBlock1(){
		return block1;
	}
	
	public Location getBlock2(){
		return block2;
	}
	
	public Set<Session> getPlayers(){
		return players;
	}
}
