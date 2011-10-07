package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class DeleteGameCommand extends nSpleefCommand {
	private final nSpleef plugin;

	public DeleteGameCommand(nSpleef plugin) {
		this.plugin = plugin;
		
		setName("deletegame");
		
		setHelp("/spleef deletegame name");
		
		setPermission("nSpleef.trusted.deletegame");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (args.length < 1){
			return false;
		}
		
		nSpleefGame game = plugin.getGame(args[0]);
		
		if (game == null){
			Messaging.send(sender, "That game doesn't exist!");
			
			return true;
		}
		
		plugin.getGames().remove(game);
		
		Messaging.send(sender, "The game '" + args[0] + "' has been deleted.");
		
		return true;
	}

}
