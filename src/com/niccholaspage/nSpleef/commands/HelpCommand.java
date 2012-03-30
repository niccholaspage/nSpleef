package com.niccholaspage.nSpleef.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.nSpleef;

public class HelpCommand implements CommandExecutor {
	private final nSpleef plugin;
	public HelpCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player)sender;
		int page;
		if (args.length < 2){
			page = 1;
		}else {
			if (plugin.isInt(args[1])){
				page = Integer.parseInt(args[1]);
			}else {
				page = 1;
			}
		}
		List<String> lines = getHelpLines(player);
		player.sendMessage(ChatColor.DARK_PURPLE + "Help Page " + page);
		int start = (page - 1) * 9;
		for (int i = start; i < lines.size() && i < start + 9; i++)
		{
			player.sendMessage(ChatColor.DARK_PURPLE + lines.get(i));
		}
		return true;
	}
	
	public List<String> getHelpLines(Player player){
		List<String> lines = new ArrayList<String>();
		Object[] executors = plugin.commandHandler.getExecutors().keySet().toArray();
		Arrays.sort(executors);
		HashMap<String, String> help = plugin.commandHandler.getHelp();
		HashMap<String, String> permissions = plugin.commandHandler.getPermissions();
		for (int i = 0; i < executors.length; i++){
			if (permissions.get(executors[i]) != "" && !PermissionHandler.has(player, permissions.get(executors[i]))){
				continue;
			}
			lines.add(help.get(executors[i]));
		}
		return lines;
	}
}
