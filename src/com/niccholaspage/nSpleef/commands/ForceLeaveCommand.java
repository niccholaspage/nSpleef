package com.niccholaspage.nSpleef.commands;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.jobs.LeaveJob;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceLeaveCommand implements CommandExecutor {
	private final nSpleef plugin;
	public ForceLeaveCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (args.length < 2) return false;
		Player player = plugin.getServer().getPlayer(args[1]);
		if (player == null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "That player doesn't exist!");
			return true;
		}
		nSpleefArena arena = Filter.getArenaByPlayer(player);
		if (arena == null){
			player.sendMessage(ChatColor.DARK_PURPLE + "That player is not in an arena!");
			return true;
		}
		if (arena.isGracePeriod()){
			player.sendMessage(ChatColor.DARK_PURPLE + "That player is currently in the grace period.");
			return true;
		}
		new LeaveJob(plugin, player, 1).run();
		return true;
	}
}
