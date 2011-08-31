package com.niccholaspage.nSpleef;

import org.bukkit.Location;
import org.bukkit.World;

public class nSpleefArena {
	private final String name;
	
	private final World world;
	
	private final Location block1;
	
	private final Location block2;
	
	public nSpleefArena(String name, World world, Location block1, Location block2){
		this.name = name;
		
		this.world = world;
		
		this.block1 = block1;
		
		this.block2 = block2;
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
}
