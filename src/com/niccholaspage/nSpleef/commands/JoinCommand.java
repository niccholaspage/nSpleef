package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.EconomyHandler;
import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.nSpleef;

public class JoinCommand implements CommandExecutor {
	public static nSpleef plugin;
	public JoinCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
	    if (!(PermissionHandler.has(player, "nSpleef.member.join"))) return true;
	    if (!(args.length == 2)) return false;
	    if (plugin.nSpleefArenas.size() == 0) return true;
		 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
			 if (plugin.nSpleefArenas.get(i).getPlayersIn().contains(player)){
				 player.sendMessage(ChatColor.DARK_PURPLE + "You are already in a game!");
				 return true;
			 }
		 }
		 if (Util.exists() == false) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");
			 return true;
		 }
		 if (plugin.nSpleefGames.size() == 0){
			 player.sendMessage(ChatColor.DARK_PURPLE + "No games!");
			 return true;
		 }
		String name = args[1];
	    for (int i = 0; i <= plugin.nSpleefGames.size() - 1; i++) {
	    	if (plugin.nSpleefGames.get(i).split(",")[0].equalsIgnoreCase(name)){
	    		for (int j = 0; j<= plugin.nSpleefArenas.size() - 1; j++){
	    			if (plugin.nSpleefGames.get(i).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(j).getName())){
	    				if (plugin.nSpleefArenas.get(j).getInGame() > 0){
	    					player.sendMessage(ChatColor.DARK_PURPLE + "A game is in progress in that arena.");
	    					return true;
	    				}
	    				if (plugin.nSpleefGames.get(i).split(",").length > 3){
	    					if (EconomyHandler.getMoney(player) < Integer.parseInt(plugin.nSpleefGames.get(i).split(",")[3])){
	    						player.sendMessage(ChatColor.DARK_PURPLE + "You do not have enough money to join that game.");
	    						return true;
	    					}
		    				EconomyHandler.removeMoney(player, Integer.parseInt(plugin.nSpleefGames.get(i).split(",")[3]));
	    				}
	    				plugin.nSpleefArenas.get(j).getPlayersLocation().add(player.getLocation().clone());
	    				plugin.nSpleefArenas.get(j).getPlayers().add(player);
	    				plugin.nSpleefArenas.get(j).getPlayersIn().add(player);
	    				plugin.nSpleefArenas.get(j).getPlayerStatus().add(false);
	    				player.teleport(plugin.nSpleefArenas.get(j).getTpBlock().toLocation(plugin.nSpleefArenas.get(j).getWorld()));
	    				player.sendMessage(ChatColor.DARK_PURPLE + "Joined game " + name + ".");
	    				player.sendMessage(ChatColor.DARK_PURPLE + "Type ready in the chat when you are ready.");
	    				return true;
	    			}
	    		}
	    	}
	    }
	    player.sendMessage(ChatColor.DARK_PURPLE + "The game " + name + " does not exist.");
		return true;
	}
}
