package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class ListCommand extends nSpleefCommand {

	public ListCommand(nSpleef plugin) {
		super(plugin);
		
		setName("list");
		
		setHelp("/spleef list");
		
		setPermission("nSpleef.member.list");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (plugin.getArenas().isEmpty()){
			sender.sendMessage(ChatColor.DARK_PURPLE + "No arenas exist!");
			
			return true;
		}
		
		sender.sendMessage(ChatColor.DARK_PURPLE + "Arenas:");
		
		for (nSpleefArena arena : plugin.getArenas()){
			sender.sendMessage(ChatColor.DARK_PURPLE + arena.getName());
		}
		
		return true;
	}

}
