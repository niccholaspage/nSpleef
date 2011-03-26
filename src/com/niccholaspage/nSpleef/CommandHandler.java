package com.niccholaspage.nSpleef;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

public class CommandHandler {
	 public static nSpleef plugin;
	 //Thanks for the idea BigBrother (specifically N3X15)
	 private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
	  static BlockVector b1loc;
	  static BlockVector b2loc;
	  public CommandHandler(nSpleef instance) {
	        plugin = instance;
	    }
	    public void registerExecutor(String subcmd, CommandExecutor cmd) {
	        executors.put(subcmd.toLowerCase(), cmd);
	    }
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("You are not a player!");
			return;
		}
		//TODO: Instead of doing this crap, actually use args
		String[] split = new String[args.length + 1];
		for (int i = 0; i < args.length; i++){
			split[i + 1] = args[i];
		}
		if (args.length < 1){
			return;
		}
		if (!(executors.containsKey(args[0]))) return;
		executors.get(args[0]).onCommand(sender, cmd, commandLabel, args);
	}
}
