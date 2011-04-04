package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.nSpleef;

public class DeleteGameCommand implements CommandExecutor {
	public static nSpleef plugin;
	public DeleteGameCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
	    if (!(PermissionHandler.has(player, "nSpleef.member.deletegame"))) return true;
		 if (args.length < 2) return false;
	    if (plugin.nSpleefGames.size() == 0){
	    	player.sendMessage(ChatColor.DARK_PURPLE + "No games exist.");
	    	return true;
	    }
		 String game = args[1];
		 String name = player.getName();
		 Boolean did = false;
		 Integer v = 0;
		 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
			 if (game.equalsIgnoreCase(plugin.nSpleefGames.get(i).split(",")[0])){
				 did = true;
				 v = i;
			 }
		 }
		 if (did == false){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That game does not exist.");
			 return true;
		 }
		 if ((name.equalsIgnoreCase(plugin.nSpleefGames.get(v).split(",")[2])) || (PermissionHandler.has(player, "nSpleef.admin.deleteanygame"))){
			 for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			 if (plugin.nSpleefGames.get(v).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
				 for (int j = 0; j < plugin.nSpleefArenas.get(i).getPlayersIn().size(); j++){
					 plugin.leave(plugin.nSpleefArenas.get(i).getPlayersIn().get(j), 2);
				 }
				 if (!(plugin.nSpleefArenas.get(i).getName() == null)) plugin.nSpleefGames.remove(plugin.nSpleefArenas.get(i).getGame());
				 plugin.nSpleefArenas.get(i).resetVars();
			 }
			 }
			 player.sendMessage(ChatColor.DARK_PURPLE + "Deleted game.");
		 }else {
			 player.sendMessage(ChatColor.DARK_PURPLE + "You did not create that game!");
		 }
		return true;
	}
}
