package com.niccholaspage.nSpleef.command.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.DefaultProperty;
import com.niccholaspage.nSpleef.Messaging;
import com.niccholaspage.nSpleef.Phrase;
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
			Messaging.send(sender, Phrase.ARENA_ALREADY_EXISTS.parse());
			
			return true;
		}
		
		Session session = plugin.getSession(player);
		
		if (session.getBlock1() == null){
			Messaging.send(player, Phrase.FIRST_POINT_NOT_SELECTED.parse());
			
			return true;
		}
		
		if (session.getBlock2() == null){
			Messaging.send(player, Phrase.SECOND_POINT_SELECTED.parse());
			
			return true;
		}
		
		if (session.getBlock1().getWorld() != session.getBlock2().getWorld()){
			Messaging.send(player, Phrase.MULTIWORLD_POINT_FAIL.parse());
			
			return true;
		}
		
		if (session.getBlock1().getY() <= session.getBlock2().getY()){
			
		}
		
		String world = session.getBlock1().getWorld().getName();
		
		File arenaConfigFile = new File(plugin.getDataFolder(), "arenas/" + args[0] + ".yml");
		
		try {
			arenaConfigFile.createNewFile();
		} catch (IOException e) {
			Messaging.send(player, Phrase.ARENA_CREATE_FAIL.parse());
			
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
		
		Messaging.send(player, Phrase.ARENA_CREATED.parse(args[0]));
		
		return true;
	}

}
