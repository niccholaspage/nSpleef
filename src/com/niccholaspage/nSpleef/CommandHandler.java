package com.niccholaspage.nSpleef;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	 public static nSpleef plugin;
	 //Thanks for the idea BigBrother (specifically N3X15)
	 private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
	  public CommandHandler(nSpleef instance) {
	        plugin = instance;
	    }
	    public void registerExecutor(String subcmd, CommandExecutor cmd) {
	        executors.put(subcmd.toLowerCase(), cmd);
	    }
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("You are not a player!");
			return true;
		}
		if (args.length < 1) return true;
		if (!(executors.containsKey(args[0]))) return true;
		executors.get(args[0]).onCommand(sender, cmd, commandLabel, args);
		return true;
	}
}
