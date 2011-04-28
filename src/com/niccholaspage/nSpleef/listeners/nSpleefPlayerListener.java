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
		  final Player player = event.getPlayer();
		  Location loc = player.getLocation();
		  BlockVector theblock = new BlockVector();
		  if (plugin.nSpleefArenas.size() == 0) return;
		  final nSpleefArena arena = Filter.getArenaByPlayerIn(player);
		  if (arena == null) return;
		  if (arena.getFirstBlock().getY() > arena.getSecondBlock().getY()) theblock = arena.getSecondBlock();
		  if (arena.getFirstBlock().getY() < arena.getSecondBlock().getY()) theblock = arena.getFirstBlock();
		  if (loc.getBlockY() + 1 <= theblock.getBlockY() + 1){
			  arena.messagePlayersIn(ChatColor.DARK_PURPLE + "[nSpleef] " + player.getDisplayName() + " is out!");
			  arena.getPlayers().remove(player);
			  arena.checkLeave();
			  player.setFireTicks(0);
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
			 nSpleefArena arena = Filter.getArenaByPlayerIn(player);
			 if (arena == null) return;
			 if (arena.getInGame() > 0) return;
			 if (!(event.getMessage().toLowerCase().contains("ready"))) return;
			 arena.getPlayerStatus().set(arena.getPlayers().indexOf(player), true);
			 arena.checkReady();
		 }
}