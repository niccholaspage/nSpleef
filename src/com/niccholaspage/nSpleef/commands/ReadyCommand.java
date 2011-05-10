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
		plugin.ready((Player)sender);
		return true;
	}
}