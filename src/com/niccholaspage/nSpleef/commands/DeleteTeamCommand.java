package com.niccholaspage.nSpleef.commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Data;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleefArena;

public class DeleteTeamCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (args.length < 3) return false;
		nSpleefArena arena = Filter.getArenaByName(args[1]);
		 if (arena == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That arena does not exist.");
			 return true;
		 }
		 boolean con = false;
		 for (int i = 0; i < arena.getTeams().size(); i++){
			 if (arena.getTeams().get(i).name().equalsIgnoreCase(args[2])){
				 con = true;
			 }
		 }
		 if (con == false){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That team doesn't exist!");
			 return true;
		 }
		 Util.openfileread("teams.txt");
		 ArrayList<String> data = Util.filetoarray();
		 Util.closefileread();
		 for (int i = 0; i < data.size(); i++){
			 if (arena.getName().equalsIgnoreCase(data.get(i).split(",")[0])){
				 if (data.get(i).split(",")[1].equalsIgnoreCase(args[2])){
					 data.remove(i);
				 }
			 }
		 }
		 new File("plugins/nSpleef/teams.txt").delete();
		 if (!(data.size() == 0)){
		 Util.openfile("teams.txt");
		 for (int i = 0; i < data.size(); i++){
			 Util.writefile(data.get(i));
		 }
		 Util.closefile();
		 }
		 Data.setupArrays();
		 return true;
	}
}
