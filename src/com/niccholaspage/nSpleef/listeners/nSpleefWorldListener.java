package com.niccholaspage.nSpleef.listeners;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

import com.niccholaspage.nSpleef.Data;

public class nSpleefWorldListener extends WorldListener {
	public void onWorldLoad(WorldLoadEvent event){
		Data.setupArenas();
	}
}
