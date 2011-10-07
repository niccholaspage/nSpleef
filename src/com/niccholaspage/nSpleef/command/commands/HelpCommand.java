package com.niccholaspage.nSpleef.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class HelpCommand extends nSpleefCommand {
	private final nSpleef plugin;
	
	public HelpCommand(nSpleef plugin) {
		this.plugin = plugin;
		
		setName("help,?");
		
		setHelp("/spleef help");
		
		setConsoleCommand(true);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		int page = 1;
		
		if (args.length > 1){
			try {
				page = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				
			}
		}
		
		Messaging.send(sender, "Spleef Help:");
		
		for (String message : getHelpLines(sender, page)){
			Messaging.send(sender, message);
		}
		
		return true;
	}
	
	private List<String> getHelpLines(CommandSender sender, int page){
		List<String> lines = new ArrayList<String>();
		
		return lines;
	}
}
