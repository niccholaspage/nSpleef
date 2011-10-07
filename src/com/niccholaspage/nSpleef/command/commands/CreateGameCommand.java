package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class CreateGameCommand extends nSpleefCommand {
	private final nSpleef plugin;
	
	public CreateGameCommand(nSpleef plugin) {
		this.plugin = plugin;
		
		setName("creategame");
		
		setHelp("/spleef creategame name arena");
		
		setPermission("nSpleef.trusted.creategame");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (args.length < 2){
			return false;
		}
		
		if (plugin.getGame(args[0]) != null){
			Messaging.send(sender, "That game already exists!");
			
			return true;
		}
		
		nSpleefArena arena = plugin.getArena(args[1]);
		
		if (arena == null){
			Messaging.send(sender, "That arena doesn't exist!");
			
			return true;
		}
		
		if (plugin.getGameByArena(arena) != null){
			Messaging.send(sender, "That arena already has a game!");
			
			return true;
		}
		
		nSpleefGame game = new nSpleefGame(args[0], arena);
		
		plugin.getGames().add(game);
		
		Messaging.send(sender, "The game '" + args[0] + "' has been created.");
		
		return true;
	}
}
