package com.niccholaspage.nSpleef.command.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class DeleteArenaCommand extends nSpleefCommand {
	public DeleteArenaCommand(nSpleef plugin) {
		super(plugin);
		
		setName("deletearena");
		
		setHelp("/spleef deletearena name");
		
		setPermission("nSpleef.admin.deletearena");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (args.length < 1){
			return false;
		}
		
		nSpleefArena arena = plugin.getArena(args[0]);
		
		if (arena == null){
			Messaging.send(sender, "That arena doesn't exist!");
			
			return true;
		}
		
		File arenaFile = new File(plugin.getArenasFolder(), arena.getName() + ".yml");
		
		arenaFile.delete();
		
		plugin.loadArenas();
		
		Messaging.send(sender, "That arena has been deleted!");
		
		return true;
	}
}
