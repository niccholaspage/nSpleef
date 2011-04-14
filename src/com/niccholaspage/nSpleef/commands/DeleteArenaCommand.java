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
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

public class DeleteArenaCommand implements CommandExecutor {
	public static nSpleef plugin;
	public DeleteArenaCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if (args.length < 2) return false;
		if (Util.exists("arenas.txt") == false) {
			player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			return true;
		}
	    ArrayList<String> data = new ArrayList<String>();
	    Util.openfileread("arenas.txt");
	    data = Util.filetoarray();
	    Util.closefileread();
	    nSpleefArena arena = Filter.getArenaByName(args[1]);
	    if (arena == null){
	    	player.sendMessage(ChatColor.DARK_PURPLE + "No arena with that name exists!");
	    	return true;
	    }
	    for (int i = 0; i < arena.getPlayersIn().size(); i++){
	    	plugin.leave(arena.getPlayersIn().get(i), 2);
	    }
	    if (!(arena.getGame() == null)) plugin.nSpleefGames.remove(arena.getGame());
	    data.remove(Filter.getArenaIndex(arena).intValue());
	    new File("plugins/nSpleef/arenas.txt").delete();
	    if (!(data.size() == 0)){
	    Util.openfile("arenas.txt");
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
