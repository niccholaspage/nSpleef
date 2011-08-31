package com.niccholaspage.nSpleef.command;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.nSpleef;

public class CommandHandler implements CommandExecutor {
	private final nSpleef plugin;
	
	private Set<nSpleefCommand> commands = new HashSet<nSpleefCommand>();
	
	public CommandHandler(nSpleef plugin){
		this.plugin = plugin;
	}
	
	public void registerCommand(nSpleefCommand command){
		commands.add(command);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 1){
			sender.sendMessage(ChatColor.DARK_PURPLE + "nSpleef " + plugin.getDescription().getVersion() + " by niccholaspage");
			
			sender.sendMessage(ChatColor.DARK_PURPLE + "Type /spleef help for help");
			
			return true;
		}
		
		nSpleefCommand command = getCommand(args[0]);
		
		if (command == null){
			return true;
		}
		
		if (!(sender instanceof Player) && !command.isConsoleCommand()){
			sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
			
			return true;
		}
		
		if (!command.getPermission().isEmpty() && !plugin.getPermissionsHandler().has(sender, command.getPermission())){
			return true;
		}
		
		//Grab all args after the skill name
		String[] realArgs = new String[args.length - 1];
		
		for (int i = 1; i < args.length; i++){
			realArgs[i - 1] = args[i];
		}
		
		if (!command.run(sender, cmd, realArgs)){
			sender.sendMessage(ChatColor.RED + command.getHelp());
		}
		
		return true;
	}
	
	private nSpleefCommand getCommand(String name){
		for (nSpleefCommand cmd : commands){
			if (cmd.getName().equalsIgnoreCase(name)){
				return cmd;
			}
		}
		
		return null;
	}
}
