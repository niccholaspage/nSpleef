package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.Volume;
import com.niccholaspage.nSpleef.nSpleef;

public class CreateGameCommand implements CommandExecutor {
	public static nSpleef plugin;
	public CreateGameCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
	    if (!nSpleef.Permissions.has(player, "nSpleef.member.creategame")) return true;
		 if (!(args.length == 3)){
			 player.sendMessage(ChatColor.RED + "/spleef creategame gamename arena");
		     return true;
		 }
		 if (Util.exists() == false) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			 return true;
		 }
		 if (!(plugin.nSpleefGames.size() == 0)){
		 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
			 if (args[2].equalsIgnoreCase(plugin.nSpleefGames.get(i).split(",")[1])){
				 player.sendMessage(ChatColor.DARK_PURPLE + "A game for that arena already exists.");
				 return true;
			 }
		 }
		 }
		 String name = args[1] + "," + args[2].toLowerCase() + "," + player.getName();
		 Boolean passed = false;
		 Integer where = 0;
		 for (int i = 0; i<= plugin.nSpleefArenas.size() - 1; i++){
		 if (args[2].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
			 passed = true;
			 where = i;
		 }
		 }
		 if (passed == false){
			 player.sendMessage(ChatColor.DARK_PURPLE + "The arena does not exist.");
			 return true;
		 }
		 if (!(plugin.nSpleefGames.size() == 0)){
		 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
			 if (args[1].equalsIgnoreCase((plugin.nSpleefGames.get(i).split(",")[0]))){
				 player.sendMessage(ChatColor.DARK_PURPLE + "A game with that name already exists.");
				 return true;
			 }
		 }
		 }
		 if (args[1].contains(",")){
			 player.sendMessage(ChatColor.DARK_PURPLE + "A game name cannot contain a comma.");
			 return true;
		 }
		 plugin.nSpleefArenas.get(where).createVolume();
		 Volume vol = plugin.nSpleefArenas.get(where).getVolume();
		 vol.setCornerOne(plugin.getServer().getWorld(plugin.nSpleefArenas.get(where).getWorld().getName()).getBlockAt(plugin.nSpleefArenas.get(where).getFirstBlock().getBlockX(), plugin.nSpleefArenas.get(where).getFirstBlock().getBlockY(), plugin.nSpleefArenas.get(where).getFirstBlock().getBlockZ()));
		 vol.setCornerTwo(plugin.getServer().getWorld(plugin.nSpleefArenas.get(where).getWorld().getName()).getBlockAt(plugin.nSpleefArenas.get(where).getSecondBlock().getBlockX(), plugin.nSpleefArenas.get(where).getSecondBlock().getBlockY(), plugin.nSpleefArenas.get(where).getSecondBlock().getBlockZ()));
		 vol.saveBlocks();
		 plugin.nSpleefGames.add(name);
		 plugin.nSpleefArenas.get(where).setMyGame(plugin.nSpleefGames.size());
		 player.sendMessage(ChatColor.DARK_PURPLE + "Game " + args[1] + " has been created.");
		return true;
	}
}
