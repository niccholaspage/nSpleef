package com.niccholaspage.nSpleef.commands;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.jobs.JoinJob;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceJoinCommand implements CommandExecutor {
	private final nSpleef plugin;
	public ForceJoinCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (args.length < 3) return false;
		Player player = plugin.getServer().getPlayer(args[1]);
		if (player == null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "That player doesn't exist!");
			return true;
		}
		nSpleefGame game = Filter.getGameByName(args[2]);
		if (game == null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "The game "  + args[2] +  " does not exist!");
			return true;
		}
		new JoinJob(plugin, player, args[2]).run();
		return true;
	}
}
