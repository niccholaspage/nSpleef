package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.EconomyHandler;
import com.niccholaspage.nSpleef.EconomyHandler.EconomyType;
import com.niccholaspage.nSpleef.Filter;
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
		 if (args.length < 3) return false;
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
		 Integer where = 0;
		 if (Filter.getArenaByName(args[2]) == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "The arena does not exist.");
			 return true; 
		 }
		 if (!(Filter.getGameByName(args[1]) == null)){
			 if (args[1].equalsIgnoreCase((Filter.getGameByName(args[1]).split(",")[0]))){
				 player.sendMessage(ChatColor.DARK_PURPLE + "A game with that name already exists.");
				 return true;
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
		 if (!(EconomyHandler.type.equals(EconomyType.NONE))){
			 if (args.length > 3){
				 if (plugin.isInt(args[3])){
					 name += "," + args[3];
				 }
			 }
			 }
		 plugin.nSpleefGames.add(name);
		 plugin.nSpleefArenas.get(where).setMyGame(plugin.nSpleefGames.size() - 1);
		 player.sendMessage(ChatColor.DARK_PURPLE + "Game " + args[1] + " has been created.");
		 if (args.length > 3){
			 if (plugin.isInt(args[3])){
				 if (!(EconomyHandler.type.equals(EconomyType.NONE))){
				 player.sendMessage(ChatColor.DARK_PURPLE + "Everyone who joins game " + args[1] + " must pay " + args[3] + " " + EconomyHandler.getCurrencyName() + ".");
				 }
			 }
		 }
		return true;
	}
}
