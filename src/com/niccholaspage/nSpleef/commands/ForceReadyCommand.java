package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleef;

public class ForceReadyCommand implements CommandExecutor {
	public static nSpleef plugin;
	public ForceReadyCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		for (int i = 0; i < plugin.nSpleefArenas.size(); i++){
			if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
				if (plugin.nSpleefArenas.get(i).getInGame() == 0){
					for (int j = 0; j < plugin.nSpleefArenas.get(i).getPlayers().size(); j++){
					 plugin.nSpleefArenas.get(i).getPlayerStatus().set(j, true);
					 plugin.nSpleefArenas.get(i).checkReady();
					}
				}
			}
		}
		return true;
	}
}