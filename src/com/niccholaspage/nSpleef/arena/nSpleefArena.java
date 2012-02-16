package com.niccholaspage.nSpleef.arena;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.player.Session;

public class nSpleefArena {
	private final String name;
	
	private final World world;
	
	private final Location block1;
	
	private final Location block2;
	
	private final Location teleportBlock;
	
	private final Set<Session> sessions;
	
	private State state;
	
	public nSpleefArena(String name, World world, Location block1, Location block2){
		this.name = name;
		
		this.world = world;
		
		this.block1 = block1;
		
		this.block2 = block2;
		
		teleportBlock = block1.clone();
		
		teleportBlock.setY(block1.getY() + 1);
		
		this.sessions = new HashSet<Session>();
		
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
		Player player = session.getPlayer();
		
		session.setArena(this);
		
		session.setOldLocation(player.getLocation().clone());
		
		session.getPlayer().teleport(teleportBlock);
		
		sessions.add(session);
		
		state = State.JOINED;
	}
	
	public void removeSession(Session session){
		session.setArena(null);
		
		session.setReady(false);
		
		session.setOldLocation(null);
		
		sessions.remove(session);
	}
	
	public void update(){
		if (state == State.JOINED && sessions.size() > 1){
			for (Session session : sessions){
				if (!session.isReady()){
					return;
				}
			}
			
			start();
		}
	}
	
	public void start(){
		
	}
	
	public void reset(){
		for (Session session : new HashSet<Session>(sessions)){
			removeSession(session);
		}
		
		//TODO: Restoring blocks
		
		state = State.IDLE;
	}
	
	public void messagePlayersIn(String message){
		for (Session session : sessions){
			Messaging.send(session.getPlayer(), message);
		}
	}
	
	public boolean canJoin(){
		return state == State.IDLE || state == State.JOINED;
	}
	
	public State getState(){
		return state;
	}
}
