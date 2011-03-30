package com.niccholaspage.nSpleef.commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Data;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;

public class DeleteArenaCommand implements CommandExecutor {
	public static nSpleef plugin;
	public DeleteArenaCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (!nSpleef.Permissions.has(player, "nSpleef.admin.deletearena")) return true;
		if (args.length < 2){
			player.sendMessage(ChatColor.RED + "/spleef deletearena arenaname");
			return true;
		}
		if (Util.exists() == false) {
			player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			return true;
		}
	    ArrayList<String> data = new ArrayList<String>();
	    Util.openfileread();
	    data = Util.filetoarray();
	    Util.closefileread();
	    Boolean pass = false;
	    for (int i = 0; i < data.size(); i++){
	    	if (data.get(i).split(":")[0].equalsIgnoreCase(args[1])){
	    		for (int j = 0; j < plugin.nSpleefGames.size(); j++){
	    			if (plugin.nSpleefGames.get(j).split(",")[1].equalsIgnoreCase(data.get(i).split(":")[0])){
	    				plugin.nSpleefGames.remove(j);
	    				break;
	    			}
	    		}
	    		data.remove(i);
	    		pass = true;
	    		break;
	    	}
	    }
	    if (pass == false){
	    	player.sendMessage(ChatColor.DARK_PURPLE + "No arena with that name exists!");
	    	return true;
	    }
	    new File("plugins/nSpleef/arenas.txt").delete();
	    if (!(data.size() == 0)){
	    Util.openfile();
	    for (int i = 0; i < data.size(); i++){
	    	Util.writefile(data.get(i) + "\n");
	    }
	    Util.closefile();
	    }
	    Data.setupArrays();
	    player.sendMessage(ChatColor.DARK_PURPLE + "Arena " + args[1] + " has been deleted.");
		return true;
	}
}
