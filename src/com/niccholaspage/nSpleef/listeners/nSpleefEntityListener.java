package com.niccholaspage.nSpleef.listeners;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;

public class nSpleefEntityListener extends EntityListener {
	public static nSpleef plugin;
	 public nSpleefEntityListener(nSpleef instance) {
    	 plugin = instance;
    }
	 @Override
	 public void onCreatureSpawn(CreatureSpawnEvent event){
		 if (plugin.nSpleefArenas.size() == 0) return;
		 for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			 if (Util.returnBlockInArea(Util.toVector(event.getLocation().getBlock()), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())){
				 event.setCancelled(true);
			 }
		 }
	 }
}
