package com.niccholaspage.nSpleef.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

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
		
		if (player.getItemInHand().getTypeId() != plugin.getConfigHandler().getItem()) return;
		
		if (!plugin.getPermissionsHandler().has(player, "nSpleef.admin.define")) return;
		
		Session session = plugin.getSession(player);
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK){
			session.setBlock1(event.getClickedBlock().getLocation());
			
			player.sendMessage(ChatColor.DARK_PURPLE + "First location set!");
		}else if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
			session.setBlock2(event.getClickedBlock().getLocation());
			
			player.sendMessage(ChatColor.DARK_PURPLE + "Second location set!");
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if (plugin.sessionExists(player)){
			plugin.getSessions().remove(plugin.getSession(player));
		}
	}
}
