//The package
package com.niccholaspage.nSpleef;
import com.niccholaspage.nSpleef.Util;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.util.BlockVector;
public class nSpleefBlockListener extends BlockListener{
	 public static nSpleef plugin;
	 public static BlockVector b1loc;
	 public static BlockVector b2loc;
	 public static World world;
	  static Boolean canplaceblocks;
	 public nSpleefBlockListener(nSpleef instance) {
    	 plugin = instance;
    }
	 public static void setConfig(Boolean c){
		 canplaceblocks = c;
	 }
	 public void onBlockPlace(BlockPlaceEvent event) {
			Player player = event.getPlayer();
			 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				 if ((plugin.nSpleefArenas.get(i).getPlayers().contains(player)) && (canplaceblocks == false)){
					 event.setCancelled(true);
					 player.sendMessage(ChatColor.DARK_PURPLE + "Cannot place blocks during spleef!");
				 }  
			}
	 }
	 public void onBlockRightClick(BlockRightClickEvent event){
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
			 if (player.getItemInHand().getTypeId() == 281){
				    if (!nSpleef.Permissions.has(player, "nSpleef.admin")) {
				        return;
				    }
				 b2loc = Util.toVector(block);
				 world = block.getWorld();
				 player.sendMessage(ChatColor.DARK_PURPLE + "Second point set.");
		 }
	 }
	 public void onBlockBreak(BlockBreakEvent event){
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 Boolean pass = false;
		 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
			 if (plugin.nSpleefArenas.get(i).getPlayersIn().contains(player)){
				 pass = true;
			 }
		 }
		 if (pass == false){
			 return;
		 }
		    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
				 if (!(Util.returnBlockInArea(Util.toVector(block), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())) == true) {
						 if (player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())){
							 player.sendMessage(ChatColor.DARK_PURPLE + "You cannot mine outside the spleef zone!");
							 event.setCancelled(true);
						 }
		    	}else {
		    		if ((player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())) && plugin.nSpleefArenas.get(i).getInGame() < 2){
		    			player.sendMessage(ChatColor.DARK_PURPLE + "You cannot mine blocks if the game hasn't started yet!");
		    			event.setCancelled(true);
		    		}
		    	}
		    }
	 }
	 public void onBlockDamage(BlockDamageEvent event) {
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 if ((player.getItemInHand().getTypeId() == 281) && (event.getDamageLevel() == BlockDamageLevel.STARTED)){
			    if (!(nSpleef.Permissions.has(player, "nSpleef.admin")) && (!(event.getDamageLevel() == BlockDamageLevel.STARTED))) {
			        return;
			    }
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
			 if (Util.exists() == false) {
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
