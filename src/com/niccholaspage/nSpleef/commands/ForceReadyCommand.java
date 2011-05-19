package com.niccholaspage.nSpleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

public class ForceReadyCommand implements CommandExecutor {
	public static nSpleef plugin;
	public ForceReadyCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		nSpleefArena arena = Filter.getArenaByPlayer((Player)sender);
		if (arena == null) return true;
		if (arena.getInGame() != 0) return true;
		if (arena.getPlayersIn().size() < 2) return true;
		arena.go();
		return true;
	}
}