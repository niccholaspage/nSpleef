package com.niccholaspage.nSpleef.command.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.Phrase;
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
		int page = 0;
		
		if (args.length > 1){
			try {
				page = Integer.parseInt(args[0]) - 1;
			}catch (NumberFormatException e){
				
			}
		}
		
		Messaging.send(sender, Phrase.HELP_PAGE.parse(page + 1 + ""));
		
		List<String> commands = getCommands(sender);
		
		int entries = 9;
		
		int start = (page * entries);
		
		Collections.sort(commands);
		
		for (int i = start; i < start + entries; i++){
			try {
				sender.sendMessage(commands.get(i));
			}catch (IndexOutOfBoundsException e){
				break;
			}
		}
		
		return true;
	}
	
	private List<String> getCommands(CommandSender sender){
		List<String> commands = new ArrayList<String>();
		
		for (nSpleefCommand command : plugin.getCommandHandler().getCommands()){
			if (!command.getPermission().isEmpty() && !plugin.getPermissionsHandler().has(sender, command.getPermission())){
				continue;
			}
			
			commands.add(command.getHelp());
		}
		
		return commands;
	}
}
