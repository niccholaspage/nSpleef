package com.niccholaspage.nSpleef.command.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
		
		YamlConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
		
		arenaConfig.set("name", args[0]);
		
		arenaConfig.set("world", world);
		
		arenaConfig.set("block1.x", session.getBlock1().getX());
		
		arenaConfig.set("block1.y", session.getBlock1().getY());
		
		arenaConfig.set("block1.z", session.getBlock1().getZ());
		
		arenaConfig.set("block2.x", session.getBlock2().getX());
		
		arenaConfig.set("block2.y", session.getBlock2().getY());
		
		arenaConfig.set("block2.z", session.getBlock2().getZ());
		
		for (DefaultProperty property : DefaultProperty.values()){
			arenaConfig.set("property." + property, property.getValue());
		}
		
		try {
			arenaConfig.save(arenaConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		plugin.loadArenas();
		
		player.sendMessage(ChatColor.DARK_PURPLE + "You've created the arena '" + args[0] + "'!");
		
		return true;
	}

}
