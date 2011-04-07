package com.niccholaspage.nSpleef;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	 public static nSpleef plugin;
	 //Thanks for the idea BigBrother (specifically N3X15)
	 private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
	 private HashMap<String, String> help = new HashMap<String, String>();
	 private HashMap<String, String> permissions = new HashMap<String, String>();
	  public CommandHandler(nSpleef instance) {
	        plugin = instance;
	    }
	    public void registerExecutor(String subcmd, CommandExecutor cmd, String help, String node) {
	        executors.put(subcmd.toLowerCase(), cmd);
	        this.help.put(subcmd.toLowerCase(), help);
	        permissions.put(subcmd.toLowerCase(), node);
	    }
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("You are not a player!");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("ready")){
			Player player = (Player) sender;
			player.chat("/spleef ready");
			return true;
		}
		if (args.length < 1) return true;
		if (!(executors.containsKey(args[0]))) return true;
		if (!(permissions.get(args[0]).equals(""))) if (!(PermissionHandler.has((Player)sender, permissions.get(args[0])))) return true;
		if (executors.get(args[0]).onCommand(sender, cmd, commandLabel, args) == false) sender.sendMessage(ChatColor.RED + help.get(args[0]));
		return true;
	}
}
