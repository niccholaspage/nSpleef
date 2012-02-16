package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.Phrase;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.arena.nSpleefArena;
import com.niccholaspage.nSpleef.command.nSpleefCommand;
import com.niccholaspage.nSpleef.player.Session;

public class JoinGameCommand extends nSpleefCommand {
	private final nSpleef plugin;
	
	public JoinGameCommand(nSpleef plugin){
		this.plugin = plugin;
		
		setName("join");
		
		setHelp("/spleef join name");
		
		setPermission("nSpleef.member.joingame");
		
		setConsoleCommand(false);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (args.length < 1){
			return false;
		}
		
		Player player = (Player) sender;
		
		Session session = plugin.getSession(player);
		
		if (session.getArena() != null){
			Messaging.send(sender, Phrase.ALREADY_IN_GAME.parse());
			
			return true;
		}
		
		nSpleefArena arena = plugin.getArena(args[0]);
		
		if (arena == null){
			Messaging.send(sender, Phrase.ARENA_DOES_NOT_EXIST.parse());
			
			return true;
		}
		
		if (!arena.canJoin()){
			Messaging.send(sender, Phrase.GAME_IN_PROGRESS.parse());
			
			return true;
		}
		
		arena.addSession(session);
		
		return true;
	}
}
