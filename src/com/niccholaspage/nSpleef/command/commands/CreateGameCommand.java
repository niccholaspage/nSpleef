package com.niccholaspage.nSpleef.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.nSpleef;
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
		
		return true;
	}
}
