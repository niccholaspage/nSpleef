package com.niccholaspage.nSpleef.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;

public class JoinJob implements Runnable {
	private final nSpleef plugin;
	private final Player player;
	private final String gameName;
	
	public JoinJob(nSpleef plugin, Player player, String gameName){
		this.plugin = plugin;
		this.player = player;
		this.gameName = gameName;
	}
	
	public void run(){
		if (Filter.getArenaByPlayer(player) != null){
			player.sendMessage(ChatColor.DARK_PURPLE + "You are already in a game!");
			return;
		}
		nSpleefGame game = Filter.getGameByName(gameName);
		if (game == null){
		    player.sendMessage(ChatColor.DARK_PURPLE + "The game " + gameName + " does not exist.");
			return;
		}
		nSpleefArena arena = Filter.getArenaByGame(game);
		if (arena.getInGame() > 0){
			player.sendMessage(ChatColor.DARK_PURPLE + "A game is in progress in that arena.");
			return;
		}
		if (game.getMoney() > 0 && plugin.method != null){
			if (plugin.method.getAccount(player.getName()).balance() < game.getMoney()){
				player.sendMessage(ChatColor.DARK_PURPLE + "You do not have enough money to join that game.");
				return;
			}
			plugin.method.getAccount(player.getName()).subtract(game.getMoney());
		}
		arena.join(player);
		player.sendMessage(ChatColor.DARK_PURPLE + "Joined game " + game.getName() + ".");
		player.sendMessage(ChatColor.DARK_PURPLE + "Type ready in the chat when you are ready.");
	}
}
