package com.niccholaspage.nSpleef.arena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.player.Session;

public class nSpleefArena {
	private final String name;
	
	private final World world;
	
	private final Location block1;
	
	private final Location block2;
	
	private final Set<Session> sessions;
	
	private final Map<String, String> properties;
	
	private State state;
	
	public nSpleefArena(String name, World world, Location block1, Location block2){
		this.name = name;
		
		this.world = world;
		
		this.block1 = block1;
		
		this.block2 = block2;
		
		this.sessions = new HashSet<Session>();
		
		this.properties = new HashMap<String, String>();
		
		state = State.IDLE;
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
	
	public Set<Session> getSessions(){
		return sessions;
	}
	
	public void addSession(Session session){
		session.setArena(this);
		
		sessions.add(session);
		
		state = State.JOINED;
	}
	
	public void reset(){
		for (Session session : sessions){
			session.setArena(null);
		}
		
		sessions.clear();
		
		//TODO: Restoring blocks
		
		state = State.IDLE;
	}
	
	public void messagePlayersIn(String message){
		for (Session session : sessions){
			Messaging.send(session.getPlayer(), message);
		}
	}
	
	public Map<String, String> getProperties(){
		return properties;
	}
	
	public String getProperty(String node){
		return properties.get(node.toLowerCase());
	}
	
	public State getState(){
		return state;
	}
}
