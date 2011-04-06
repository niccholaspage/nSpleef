package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleef;

public class ReadyCommand implements CommandExecutor {
	public static nSpleef plugin;
	public ReadyCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (plugin.nSpleefGames.size() == 0) return true;
			 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
					 if (plugin.nSpleefArenas.get(i).getInGame() == 0){
						 Integer where = plugin.nSpleefArenas.get(i).getPlayers().indexOf(player);
						 plugin.nSpleefArenas.get(i).getPlayerStatus().set(where, true);
						 plugin.nSpleefArenas.get(i).checkReady();
					 }
				 }
			 }
		return true;
	}
}