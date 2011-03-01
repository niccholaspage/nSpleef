//The package
package com.niccholaspage.nSpleef;
//All the imports

//import org.bukkit.Material;
//import org.bukkit.block.Block;
import com.niccholaspage.nSpleef.Util;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockDamageLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.util.BlockVector;
//Start the class nSpleefBlockListener
public class nSpleefBlockListener extends BlockListener{
	 public static nSpleef plugin;
	  static BlockVector b1loc;
	  static BlockVector b2loc;
	  static World world;
	  static Boolean canplaceblocks;
	 public nSpleefBlockListener(nSpleef instance) {
    	 plugin = instance;
    }
	 public static void setConfig(Boolean c){
		 canplaceblocks = c;
	 }
	 public static BlockVector returnblock(int a0){
		 switch (a0){
		 case 1: return b1loc;
		 case 2: return b2loc;
		 default: return b1loc;
		 }
	 }
	 public static World returnworld(){
		 return world;
	 }
	 public static Boolean returnblockinarea(BlockVector block, BlockVector b1, BlockVector b2){
		 if ((block.getBlockX() >= b1.getBlockX()) && (block.getBlockX() <= b2.getBlockX()) || 
				 (block.getBlockX() >= b2.getBlockX()) && (block.getBlockX() <= b1.getBlockX())){
			 if ((block.getBlockY() >= b1.getBlockY()) && (block.getBlockY() <= b2.getBlockY()) || 
				(block.getBlockY() >= b2.getBlockY()) && (block.getBlockY() <= b1.getBlockY())){
				 if ((block.getBlockZ() >= b1.getBlockZ()) && (block.getBlockZ() <= b2.getBlockZ()) ||
				(block.getBlockZ() >= b2.getBlockZ()) && (block.getBlockZ() <= b1.getBlockZ())){
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 //This method is called when ever a block is placed.
	 public void onBlockPlace(BlockPlaceEvent event) {
		 //Get the player doing the placing
			Player player = event.getPlayer();
			 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				 if ((plugin.nSpleefArenas.get(i).getPlayers().contains(player)) && (canplaceblocks == false)){
					 event.setCancelled(true);
					 player.sendMessage(ChatColor.DARK_PURPLE + "Cannot place blocks during spleef!");
				 }
				 /*for (int j = 0; j <= plugin.nSpleefArenas.get(i).getPlayers().size() - 1; j++){
					 if ((player.equals(plugin.nSpleefArenas.get(i).getPlayers().get(j)) && canplaceblocks == false)){
						event.setCancelled(true);
						player.sendMessage(ChatColor.DARK_PURPLE + "Cannot place blocks during spleef!");
					}
				}*/
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
	 public void onBlockDamage(BlockDamageEvent event) {
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 if ((player.getItemInHand().getTypeId() == 281) && (event.getDamageLevel() == BlockDamageLevel.STARTED)){
			    if ((!nSpleef.Permissions.has(player, "nSpleef.admin")) && (!(event.getDamageLevel() == BlockDamageLevel.STARTED))) {
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
				 if (plugin.nSpleefArenas.get(i).getInGame() == false){
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
			 Boolean did = false;
			    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
					 if ((returnblockinarea(Util.toVector(block), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())) == true) {
							 if (player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())){
								 did = true;
							 }
			    	}
			    }
			    if (did == false){
			    	player.sendMessage(ChatColor.DARK_PURPLE + "Cannot mine outside of spleef zone!");
			    	event.setCancelled(true);
			    	return;
			    }
			 if ((!(block.getTypeId() == 7)) && (did == true)){
				 block.setTypeId(0);
			 }
	 }
	 }
