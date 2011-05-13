package com.niccholaspage.nSpleef.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldListener;

import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

public class nSpleefWorldListener extends WorldListener {
	private nSpleef plugin;
	
	public nSpleefWorldListener(nSpleef plugin) {
		this.plugin = plugin;
	}
	
	public void onChunkUnload(ChunkUnloadEvent event){
		for (nSpleefArena arena : plugin.nSpleefArenas){
			Block block1 = Util.toBlockFromVector(arena.getFirstBlock(), arena.getWorld());
			Block block2 = Util.toBlockFromVector(arena.getSecondBlock(), arena.getWorld());
			Chunk chunk = event.getChunk();
			if (block1.getChunk() == chunk || block2.getChunk() == chunk){
				event.setCancelled(true);
				return;
			}
			for (Location location : arena.getPlayersLocation()){
				if (location.getBlock().getChunk() == chunk){
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
