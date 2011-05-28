package com.niccholaspage.nSpleef.commands;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.jobs.LeaveJob;

import org.bukkit.ChatColor;
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
		if (args.length < 1) return false;
		Player player = plugin.getServer().getPlayer(args[0]);
		if (player == null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "That player doesn't exist!");
			return true;
		}
		new LeaveJob(plugin, player, 1).run();
		return true;
	}
}
