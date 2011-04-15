//The Package
package com.niccholaspage.nSpleef.listeners;
//All the imports
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.BlockVector;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
//Starts the class nSpleefPlayer listener
public class nSpleefPlayerListener extends PlayerListener{
	 public static nSpleef plugin;
	  public nSpleefPlayerListener(nSpleef instance) {
	        plugin = instance;
	    }
	  @Override
	  public void onPlayerQuit(PlayerQuitEvent event){
		  plugin.leave(event.getPlayer(), 0);
	  }
	  @Override
	  public void onPlayerMove(PlayerMoveEvent event){
		  Player player = event.getPlayer();
		  Location loc = player.getLocation();
		  BlockVector theblock = new BlockVector();
		  if (plugin.nSpleefArenas.size() == 0) return;
		  nSpleefArena arena = Filter.getArenaByPlayerIn(player);
		  if (arena == null) return;
		  if (arena.getFirstBlock().getY() > arena.getSecondBlock().getY()) theblock = arena.getSecondBlock();
		  if (arena.getFirstBlock().getY() < arena.getSecondBlock().getY()) theblock = arena.getFirstBlock();
		  if (loc.getBlockY() + 1 <= theblock.getBlockY() + 1){
			  for (int i = 0; i < arena.getPlayersIn().size(); i++){
				  arena.getPlayersIn().get(i).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] " + player.getDisplayName() + " is out!");
				  arena.getPlayers().remove(player);
				  if (arena.getPlayers().size() == 1){
					  event.setFrom(arena.getPlayersLocation().get(arena.getPlayersIn().indexOf(player)));
					  event.setTo(arena.getPlayersLocation().get(arena.getPlayersIn().indexOf(player)));
				  }
					 arena.checkLeave();
			  }
		  }
	  }
		 public void onPlayerMove2(PlayerMoveEvent event){
			 Player player = event.getPlayer();
			 Location loc = player.getLocation();
			 Boolean pass = false;
			 if (plugin.nSpleefArenas.size() == 0) return;
			 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
					 pass = true;
				 }
			 }
			 if (pass == false){
				 return;
			 }
			 BlockVector theblock = new BlockVector();
				    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
						 if ((Util.returnBlockInArea(Util.toVector(loc.getBlock()), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())) == true) {
								 if (player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())){
									 Boolean con = false;
									 for (int j = 0; j<= plugin.nSpleefGames.size() - 1; j++){
										 if (plugin.nSpleefGames.get(j).getArena().equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
											 con = true;
											 break;
										 }
									 }
									 if (con == false){
										 return;
									 }
									 if (plugin.nSpleefArenas.get(i).getFirstBlock().getBlockY() > plugin.nSpleefArenas.get(i).getSecondBlock().getBlockY()){
										 theblock = plugin.nSpleefArenas.get(i).getSecondBlock();
									 }
									 if (plugin.nSpleefArenas.get(i).getFirstBlock().getBlockY() < plugin.nSpleefArenas.get(i).getSecondBlock().getBlockY()){
										 theblock = plugin.nSpleefArenas.get(i).getFirstBlock();
									 } 
										 if (loc.getBlockY() + 1 <= theblock.getBlockY() + 1){
											 for (int k = 0; k <= plugin.nSpleefArenas.size() - 1; k++){
												 if (plugin.nSpleefArenas.get(k).getPlayers().contains(player)){
													 for (int j = 0; j < plugin.nSpleefArenas.get(k).getPlayersIn().size(); j++){
														 plugin.nSpleefArenas.get(k).getPlayersIn().get(j).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] " + player.getDisplayName() + " is out!");
													 }
													 plugin.nSpleefArenas.get(k).getPlayers().remove(player);
													 if (plugin.nSpleefArenas.get(k).getPlayers().size() == 1){
													 event.setFrom(plugin.nSpleefArenas.get(k).getPlayersLocation().get(plugin.nSpleefArenas.get(k).getPlayersIn().indexOf(player)));
													 event.setTo(plugin.nSpleefArenas.get(k).getPlayersLocation().get(plugin.nSpleefArenas.get(k).getPlayersIn().indexOf(player)));
													 }
													 plugin.nSpleefArenas.get(k).checkLeave();
													 return;
												 }
											 }
										 }
								 }
				    	}
				    }
		 }
	  @Override
		 public void onPlayerInteract(PlayerInteractEvent event){
			 if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
			 Player player = event.getPlayer();
			 Block block = event.getClickedBlock();
				 if (player.getItemInHand().getTypeId() == 281){
					 if (!(PermissionHandler.has(player, "nSpleef.admin.define"))) return;
					 nSpleefBlockListener.b2loc = Util.toVector(block);
					 nSpleefBlockListener.world = block.getWorld();
					 player.sendMessage(ChatColor.DARK_PURPLE + "Second point set.");
			 }
		 }
	  @Override
		 public void onPlayerChat(PlayerChatEvent event){
			 Player player = event.getPlayer();
			 if (plugin.nSpleefGames.size() == 0) return;
			 if (event.getMessage().toLowerCase().contains("ready")){
				 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
					 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
						 if (plugin.nSpleefArenas.get(i).getInGame() == 0){
							 Integer where = plugin.nSpleefArenas.get(i).getPlayers().indexOf(player);
							 plugin.nSpleefArenas.get(i).getPlayerStatus().set(where, true);
							 plugin.nSpleefArenas.get(i).checkReady();
						 }
					 }
				 }
			 }
		 }
}