package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.EconomyHandler;
import com.niccholaspage.nSpleef.nSpleef;

public class ListCommand implements CommandExecutor {
	public static nSpleef plugin;
	public ListCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (plugin.nSpleefArenas.size() == 0) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");
			 return true;
		 }
		 player.sendMessage(ChatColor.DARK_PURPLE + "Arenas:");
	    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
	    	player.sendMessage(ChatColor.DARK_PURPLE + plugin.nSpleefArenas.get(i).getName());
	    	}
	    if (!(plugin.nSpleefGames.size() == 0)){
	    	player.sendMessage(ChatColor.DARK_PURPLE + "Games:");
			 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
				 String send = ChatColor.DARK_PURPLE + plugin.nSpleefGames.get(i).getName() + " in arena " + plugin.nSpleefGames.get(i).getArena();
				 if (plugin.nSpleefGames.get(i).getMoney() > 0){
					 send += ";Cost:" + plugin.nSpleefGames.get(i).getMoney() + " " + EconomyHandler.getCurrencyName();
				 }
				 player.sendMessage(send);
			 }
	    }else {
	    	player.sendMessage(ChatColor.DARK_PURPLE + "No games.");
	    }
		return true;
	}
}
