package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleef;

public class ReadyCommand implements CommandExecutor {
	private final nSpleef plugin;
	public ReadyCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		plugin.ready((Player)sender);
		return true;
	}
}