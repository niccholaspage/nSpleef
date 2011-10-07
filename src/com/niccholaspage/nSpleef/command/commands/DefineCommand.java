package com.niccholaspage.nSpleef.command.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nSpleef.DefaultProperty;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.command.nSpleefCommand;
import com.niccholaspage.nSpleef.player.Session;

public class DefineCommand extends nSpleefCommand {
	private final nSpleef plugin;
	public DefineCommand(nSpleef plugin) {
		this.plugin = plugin;
		
		setName("define");
		
		setHelp("/spleef define arena");
		
		setPermission("nSpleef.admin.define");
		
		setConsoleCommand(false);
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		if (args.length < 1){
			return false;
		}
		
		Player player = (Player) sender;
		
		if (plugin.getArena(args[0]) != null){
			sender.sendMessage(ChatColor.DARK_PURPLE + "That arena already exists!");
			
			return true;
		}
		
		Session session = plugin.getSession(player);
		
		if (session.getBlock1() == null){
			sender.sendMessage(ChatColor.RED + "You haven't chosen your first point!");
			
			return true;
		}
		
		if (session.getBlock2() == null){
			sender.sendMessage(ChatColor.RED + "You haven't chosen your second point!");
			
			return true;
		}
		
		if (session.getBlock1().getWorld() != session.getBlock2().getWorld()){
			player.sendMessage(ChatColor.RED + "The two selected blcoks are in different worlds!");
			
			return true;
		}
		
		String world = session.getBlock1().getWorld().getName();
		
		File arenaConfigFile = new File(plugin.getDataFolder(), "arenas/" + args[0] + ".yml");
		
		try {
			arenaConfigFile.createNewFile();
		} catch (IOException e) {
			player.sendMessage(ChatColor.RED + "Could not create the arena!");
			
			return true;
		}
		
		Configuration arenaConfig = new Configuration(arenaConfigFile);
		
		arenaConfig.load();
		
		arenaConfig.setProperty("name", args[0]);
		
		arenaConfig.setProperty("world", world);
		
		arenaConfig.setProperty("block1.x", session.getBlock1().getX());
		
		arenaConfig.setProperty("block1.y", session.getBlock1().getY());
		
		arenaConfig.setProperty("block1.z", session.getBlock1().getZ());
		
		arenaConfig.setProperty("block2.x", session.getBlock2().getX());
		
		arenaConfig.setProperty("block2.y", session.getBlock2().getY());
		
		arenaConfig.setProperty("block2.z", session.getBlock2().getZ());
		
		for (DefaultProperty property : DefaultProperty.values()){
			arenaConfig.setProperty("property." + property, property.getValue());
		}
		
		arenaConfig.save();
		
		plugin.loadArenas();
		
		player.sendMessage(ChatColor.DARK_PURPLE + "You've created the arena '" + args[0] + "'!");
		
		return true;
	}

}
