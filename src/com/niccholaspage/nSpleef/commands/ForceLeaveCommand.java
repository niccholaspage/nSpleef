package com.niccholaspage.nSpleef.commands;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.jobs.LeaveJob;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceLeaveCommand implements CommandExecutor {
	public nSpleef plugin;
	public ForceLeaveCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		new LeaveJob(plugin, (Player) sender, 1).run();
		return true;
	}
}
