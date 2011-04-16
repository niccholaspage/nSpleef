package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Data;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleefArena;

public class CreateTeamCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (args.length < 3) return false;
		 if (Util.exists("arenas.txt") == false) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			 return true;
		 }
		 nSpleefArena arena = Filter.getArenaByName(args[1]);
		 if (arena == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That arena does not exist.");
			 return true;
		 }
		 boolean con = false;
		 for (int i = 0; i < DyeColor.values().length; i++){
			 if (DyeColor.values()[i].name().equalsIgnoreCase(args[2])){
				 if (arena.getTeams().contains(DyeColor.values()[i])){
					 player.sendMessage(ChatColor.DARK_PURPLE + "That team already exists!");
					 return true;
				 }
				 con = true;
			 }
		 }
		 if (con == false){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That is not a color! Remember, you can only use wool colors.");
			 return true;
		 }
		 Util.openfile("teams.txt");
		 Util.writefile(arena.getName() + "," + args[2].toLowerCase() + "\n");
		 Util.closefile();
		 Data.setupArrays();
		 player.sendMessage(ChatColor.DARK_PURPLE + "Team " + args[2].toLowerCase() + " has been made.");
		return true;
	}
}
