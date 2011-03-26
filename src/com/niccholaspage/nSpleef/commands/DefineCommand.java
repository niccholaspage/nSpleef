package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import com.niccholaspage.nSpleef.Data;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefBlockListener;

public class DefineCommand implements CommandExecutor {
	public static nSpleef plugin;
	public DefineCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (!nSpleef.Permissions.has(player, "nSpleef.admin.define")) return true;
		if (args.length < 2){
			player.sendMessage(ChatColor.RED + "/spleef define arenaname");
			return true;
		}
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
		 for (int i = 0; i<= plugin.nSpleefArenas.size() - 1; i++){
			 if (args[1].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
				 player.sendMessage(ChatColor.DARK_PURPLE + "An arena with that name already exists.");
				 return true;
			 }
		 }
		 String name = args[1];
		 if (b1loc.getBlockY() == b2loc.getBlockY()){
			 player.sendMessage(ChatColor.DARK_PURPLE + "Both blocks cannot be on the same level. If you want a one level spleef arena, make the second point 2 or more blocks below the arena.");
			 return true;
		 }
		 Util.openfile();
		 if (b1loc.getBlockY() > b2loc.getBlockY()){
			 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + b1loc.getBlockY() + ":" + b1loc.getBlockZ()
					 + ":" + b2loc.getBlockX() + ":" + (b2loc.getBlockY() + 1) + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
		 }else {
			 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + (b1loc.getBlockY() + 1) + ":" + b1loc.getBlockZ()
					 + ":" + b2loc.getBlockX() + ":" + b2loc.getBlockY() + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
		 }
		 Util.closefile();
		 Data.setupArrays();
		 player.sendMessage(ChatColor.DARK_PURPLE + "Arena " + name + " has been made!");
		return true;
	}
}
