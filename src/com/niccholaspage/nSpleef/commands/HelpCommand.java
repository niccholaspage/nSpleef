package com.niccholaspage.nSpleef.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.nSpleef;

public class HelpCommand implements CommandExecutor {
	public nSpleef plugin;
	public HelpCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player)sender;
		String[] executors = (String[])plugin.commandHandler.getExecutors().keySet().toArray();
		HashMap<String, String> help = plugin.commandHandler.getHelp();
		HashMap<String, String> permissions = new HashMap<String, String>();
		for (int i = 0; i < executors.length; i++){
			if (PermissionHandler.has(player, permissions.get(executors[i]))){
				player.sendMessage(ChatColor.DARK_PURPLE + help.get(executors[i]));
			}
		}
		return true;
	}
}
