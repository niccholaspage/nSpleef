//The package
package com.niccholaspage.nSpleef.listeners;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.util.BlockVector;
public class nSpleefBlockListener extends BlockListener{
	 public static nSpleef plugin;
	 public static BlockVector b1loc;
	 public static BlockVector b2loc;
	 public static World world;
	 public nSpleefBlockListener(nSpleef instance) {
    	 plugin = instance;
    }
	 @Override
	 public void onBlockPlace(BlockPlaceEvent event) {
			Player player = event.getPlayer();
			if (Filter.getArenaByPlayer(player) == null) return;
			if (plugin.canPlaceBlocks == false){
				 event.setCancelled(true);
				 player.sendMessage(ChatColor.DARK_PURPLE + "Cannot place blocks during spleef!");				
			}
	 }
	 @Override
	 public void onBlockBreak(BlockBreakEvent event){
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 nSpleefArena arena = Filter.getArenaByPlayer(player);
		 if (arena == null) return;
		 if (!(player.getWorld() == arena.getWorld())) return;
		 if (!(Util.returnBlockInArea(Util.toVector(block), arena.getFirstBlock(), arena.getSecondBlock()))){
			 player.sendMessage(ChatColor.DARK_PURPLE + "You cannot mine outside the spleef zone!");
			 event.setCancelled(true);
		 }else {
 			player.sendMessage(ChatColor.DARK_PURPLE + "You cannot mine blocks if the game hasn't started yet!");
			event.setCancelled(true);
		 }
	 }
	 @Override
	 public void onBlockDamage(BlockDamageEvent event) {
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 if (player.getItemInHand().getTypeId() == 281){
			    if (!(PermissionHandler.has(player, "nSpleef.admin.define"))) return;
			    b1loc = Util.toVector(block);
			    world = block.getWorld();
			    player.sendMessage(ChatColor.DARK_PURPLE + "First point set.");
			    return;
		 }
		 Boolean pass = false;
		 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
			 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
				 pass = true;
				 if ((plugin.nSpleefArenas.get(i).getInGame() == 0) || (plugin.nSpleefArenas.get(i).getInGame() == 1)){
					 return;
				 }
			 }
		 }
		 if (pass == false){
			 return;
		 }
			 if (Util.exists("arenas.txt") == false) {
				 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");
				 return;
			 }
			    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
					 if ((Util.returnBlockInArea(Util.toVector(block), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())) == true) {
							 if (player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())){
								 if ((!(block.getTypeId() == 7))){
									 block.setTypeId(0);
								 }
								 return;
							 }
			    	}
			    }
	 }
	 }
