package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.arena.nSpleefArena;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class ListCommand extends nSpleefCommand {
	private final nSpleef plugin;
	
	public ListCommand(nSpleef plugin) {
		this.plugin = plugin;
		
		setName("list");
		
		setHelp("/spleef list");
		
		setPermission("nSpleef.member.list");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (plugin.getArenas().isEmpty()){
			Messaging.send(sender, "No arenas exist!");
			
			return true;
		}
		
		Messaging.send(sender, "Arenas:");
		
		for (nSpleefArena arena : plugin.getArenas()){
			Messaging.send(sender, arena.getName());
		}
		
		if (plugin.getGames().isEmpty()) return true;
		
		Messaging.send(sender, "Games:");
		
		for (nSpleefGame game : plugin.getGames()){
			Messaging.send(sender, game.getName() + " in arena " + game.getArena().getName());
			
			return true;
		}
		
		return true;
	}

}
