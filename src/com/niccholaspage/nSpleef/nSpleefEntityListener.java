package com.niccholaspage.nSpleef;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class nSpleefEntityListener extends EntityListener {
	public static nSpleef plugin;
	 public nSpleefEntityListener(nSpleef instance) {
    	 plugin = instance;
    }
	 public void onCreatureSpawn(CreatureSpawnEvent event){
		 if (plugin.nSpleefArenas.size() == 0) return;
		 for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			 if (Util.returnBlockInArea(Util.toVector(event.getLocation().getBlock()), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())){
				 event.setCancelled(true);
			 }
		 }
	 }
}
