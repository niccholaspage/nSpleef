package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.jobs.JoinJob;

public class JoinCommand implements CommandExecutor {
	public static nSpleef plugin;
	public JoinCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (args.length < 2) return false;
		new JoinJob(plugin, (Player)sender, args[1]).run();
		return true;
	}
}
