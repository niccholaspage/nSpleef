package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class CreateGameCommand extends nSpleefCommand {

	public CreateGameCommand(nSpleef plugin) {
		super(plugin);
		
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
			sender.sendMessage(ChatColor.DARK_PURPLE + "That game already exists!");
			
			return true;
		}
		
		nSpleefArena arena = plugin.getArena(args[1]);
		
		if (arena == null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "That arena doesn't exist!");
			
			return true;
		}
		
		if (plugin.getGameByArena(arena) != null){
			sender.sendMessage(ChatColor.RED + "That arena already has a game!");
			
			return true;
		}
		
		nSpleefGame game = new nSpleefGame(args[0], arena);
		
		plugin.getGames().add(game);
		
		sender.sendMessage(ChatColor.DARK_PURPLE + "The game '" + args[0] + "' has been created.");
		
		return true;
	}
}
