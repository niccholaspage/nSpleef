package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;

public class JoinCommand implements CommandExecutor {
	public static nSpleef plugin;
	public JoinCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
	    if (args.length < 2) return false;
		if (Filter.getArenaByPlayer(player) != null){
			player.sendMessage(ChatColor.DARK_PURPLE + "You are already in a game!");
			return true;
		}
		String name = args[1];
		nSpleefGame game = Filter.getGameByName(name);
		if (game == null){
		    player.sendMessage(ChatColor.DARK_PURPLE + "The game " + name + " does not exist.");
			return true;
		}
		nSpleefArena arena = Filter.getArenaByGame(game);
		if (arena.getInGame() > 0){
			player.sendMessage(ChatColor.DARK_PURPLE + "A game is in progress in that arena.");
			return true;
		}
		if (game.getMoney() > 0 && plugin.method != null){
			if (plugin.method.getAccount(player.getName()).balance() < game.getMoney()){
				player.sendMessage(ChatColor.DARK_PURPLE + "You do not have enough money to join that game.");
				return true;
			}
			plugin.method.getAccount(player.getName()).subtract(game.getMoney());
		}
		arena.join(player);
		player.sendMessage(ChatColor.DARK_PURPLE + "Joined game " + name + ".");
		player.sendMessage(ChatColor.DARK_PURPLE + "Type ready in the chat when you are ready.");
		return true;
	}
}
