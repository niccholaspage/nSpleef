package com.niccholaspage.nSpleef.listeners;

import org.bukkit.Location;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldListener;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

public class nSpleefWorldListener extends WorldListener {
	private nSpleef plugin;
	
	public nSpleefWorldListener(nSpleef plugin) {
		this.plugin = plugin;
	}
	
	public void onChunkUnload(ChunkUnloadEvent event){
		for (nSpleefArena arena : plugin.nSpleefArenas){
			for (Location location : arena.getPlayersLocation()){
				if (location.getBlock().getChunk() == event.getChunk()){
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
