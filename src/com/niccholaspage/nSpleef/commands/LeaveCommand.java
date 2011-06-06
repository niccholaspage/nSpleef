package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.jobs.LeaveJob;

public class LeaveCommand implements CommandExecutor {
	private final nSpleef plugin;
	public LeaveCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		new LeaveJob(plugin, (Player) sender, 1).run();
		return true;
	}
}
