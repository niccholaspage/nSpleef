package com.niccholaspage.nSpleef.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.EconomyHandler;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;

public class LeaveJob implements Runnable {
	private final nSpleef plugin;
	private final Player player;
	private final int mode;
	
	public LeaveJob(nSpleef plugin, Player player, int mode){
		this.plugin = plugin;
		this.player = player;
		this.mode = mode;
	}
	
	public void run(){
		//Mode 0: Disconnect
		//Mode 1: Leave
		//Mode 2: Deleted arena or game
		//Mode 3: Kicked
		nSpleefArena arena = Filter.getArenaByPlayer(player);
		int index = arena.getPlayersIn().indexOf(player);
	 	arena.getPlayerStatus().remove(index);
	 	arena.getPlayersIn().remove(index);
	 	arena.getPlayers().remove(player);
	 	player.teleport(arena.getPlayersLocation().get(index));
	 	arena.getPlayersLocation().remove(index);
		if (arena.getGame().getMoney() > 0){
			int money = arena.getGame().getMoney();
			if (mode == 2) EconomyHandler.addMoney(player, money);
			if ((mode == 1) && (plugin.giveMoneyOnLeave)) EconomyHandler.addMoney(player, money);
			if ((mode == 0) && (plugin.giveMoneyOnDisconnect)) EconomyHandler.addMoney(player, money);
			if ((mode == 3) && (plugin.giveMoneyOnKick)) EconomyHandler.addMoney(player, money);
		}
		switch (mode){
		case 3:
			player.sendMessage(ChatColor.DARK_PURPLE + "You were kicked from the spleef game!"); break;
		case 2:
			player.sendMessage(ChatColor.DARK_PURPLE + "The game/arena you were playing on has been deleted!"); break;
		default:
			player.sendMessage(ChatColor.DARK_PURPLE + "You've left the spleef game."); break;
	}
	arena.leave(player);
	}
}
