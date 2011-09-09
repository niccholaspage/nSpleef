package com.niccholaspage.nSpleef.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.player.Session;

public class nSpleefPlayerListener extends PlayerListener {
	private final nSpleef plugin;
	
	public nSpleefPlayerListener(nSpleef plugin){
		this.plugin = plugin;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if (player.getItemInHand() == null) return;
		
		if (player.getItemInHand().getTypeId() == plugin.getConfigHandler().getItem() && plugin.getPermissionsHandler().has(player, "nSpleef.admin.define")){
			
			Session session = plugin.getSession(player);
			
			if (event.getAction() == Action.LEFT_CLICK_BLOCK){
				session.setBlock1(event.getClickedBlock().getLocation());
				
				Messaging.send(player, "First location set!");
			}else if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
				session.setBlock2(event.getClickedBlock().getLocation());
				
				Messaging.send(player, "Second location set!");
			}
		}
	}
	
	
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		
		Session session = plugin.getSession(player);
		
		if (session.getArena() == null) return;
		
	    String[] split = event.getMessage().split(" ");
	    
	    if (split.length < 1) return;

	    String cmd = split[0].trim().substring(1).toLowerCase();

	    if (!cmd.equalsIgnoreCase("spleef")) {
	      event.setCancelled(true);
	      
	      event.setMessage("hahahahahahahahahahahahano");
	      
	      Messaging.send(player, "You cannot use that command while in a spleef game.");
	    }
	}
	
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if (plugin.sessionExists(player)){
			plugin.getSessions().remove(plugin.getSession(player));
		}
	}
}
