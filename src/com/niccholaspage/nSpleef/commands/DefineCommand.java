package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import com.niccholaspage.nSpleef.Data;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.listeners.nSpleefBlockListener;

public class DefineCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (args.length < 2) return false;
		if (args[1].contains(",")){
			 player.sendMessage(ChatColor.DARK_PURPLE + "An arena name cannot contain a comma.");
			 return true;
		}
	    BlockVector b1loc = nSpleefBlockListener.b1loc;
	    BlockVector b2loc = nSpleefBlockListener.b2loc;
		 if ((b1loc == null) || (b2loc == null)){
			 player.sendMessage(ChatColor.DARK_PURPLE + "Positions aren't set.");
			 return true;
		 }
		 if (!(Filter.getArenaByName(args[1]) == null)){
			 player.sendMessage(ChatColor.DARK_PURPLE + "An arena with that name already exists.");
			 return true;
		 }
		 String name = args[1];
		 if (b1loc.getBlockY() == b2loc.getBlockY()){
			 player.sendMessage(ChatColor.DARK_PURPLE + "Both blocks cannot be on the same level. If you want a one level spleef arena, make the second point 2 or more blocks below the arena.");
			 return true;
		 }
		 Util.openfile("arenas.txt");
		 if (b1loc.getBlockY() > b2loc.getBlockY()){
			 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + b1loc.getBlockY() + ":" + b1loc.getBlockZ()
					 + ":" + b2loc.getBlockX() + ":" + (b2loc.getBlockY() + 1) + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
		 }else {
			 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + (b1loc.getBlockY() + 1) + ":" + b1loc.getBlockZ()
					 + ":" + b2loc.getBlockX() + ":" + b2loc.getBlockY() + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
		 }
		 Util.closefile();
		 Data.setupArenas();
		 player.sendMessage(ChatColor.DARK_PURPLE + "Arena " + name + " has been made!");
		return true;
	}
}
