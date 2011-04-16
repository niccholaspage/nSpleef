package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleefArena;

public class ListTeamsCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (args.length < 2) return false;
		 if (Util.exists("arenas.txt") == false) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			 return true;
		 }
		 nSpleefArena arena = Filter.getArenaByName(args[1]);
		 if (arena == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That arena does not exist.");
			 return true;
		 }
		 if (arena.getTeams().size() == 0){
			 player.sendMessage(ChatColor.DARK_PURPLE + "No teams exist for this arena!");
			 return true;
		 }
		 for (int i = 0; i < arena.getTeams().size(); i++){
			 player.sendMessage(ChatColor.DARK_PURPLE + arena.getTeams().get(i).name().toLowerCase());
		 }
		 return true;
	 }
}
