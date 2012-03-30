package com.niccholaspage.nSpleef.jobs;

import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleefArena;

public class ReadyJob implements Runnable {
	private final Player player;
	public ReadyJob(Player player){
		this.player = player;
	}
	
	public void run() {
		 nSpleefArena arena = Filter.getArenaByPlayerIn(player);
		 if (arena == null) return;
		 if (arena.getInGame() > 0) return;
		 arena.getPlayerStatus().set(arena.getPlayers().indexOf(player), true);
		 arena.checkReady();
	}

}
