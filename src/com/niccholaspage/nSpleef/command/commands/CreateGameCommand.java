package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.Phrase;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.arena.nSpleefArena;
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
			Messaging.send(sender, Phrase.GAME_ALREADY_EXISTS.parse());
			
			return true;
		}
		
		nSpleefArena arena = plugin.getArena(args[1]);
		
		if (arena == null){
			Messaging.send(sender, Phrase.ARENA_DOES_NOT_EXIST.parse());
			
			return true;
		}
		
		if (plugin.getGameByArena(arena) != null){
			Messaging.send(sender, Phrase.ARENA_ALREADY_HAS_GAME.parse());
			
			return true;
		}
		
		nSpleefGame game = new nSpleefGame(args[0], arena);
		
		plugin.getGames().add(game);
		
		Messaging.send(sender, Phrase.GAME_CREATED.parse(args[0]));
		
		return true;
	}
}
