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
	 public HashMap<String, CommandExecutor> getExecutors(){
		 return executors;
	 }
	 public HashMap<String, String> getHelp(){
		 return help;
	 }
	 public HashMap<String, String> getPermissions(){
		 return permissions;
	 }
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("You are not a player!");
			return true;
		}
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("ready")){
			plugin.ready(player);
			return true;
		}
		if (args.length < 1){
			player.sendMessage(ChatColor.DARK_PURPLE + "nSpleef " + plugin.getDescription().getVersion());
			player.sendMessage(ChatColor.DARK_PURPLE + "Type /spleef help or /spleef ? for help.");
			return true;
		}
		if (!(executors.containsKey(args[0]))) return true;
		if (permissions.get(args[0]) != "") if (!PermissionHandler.has(player, permissions.get(args[0]))) return true;
		if (executors.get(args[0]).onCommand(sender, cmd, commandLabel, args) == false) sender.sendMessage(ChatColor.RED + help.get(args[0]));
		return true;
	}
}
