package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;
import com.niccholaspage.nSpleef.jobs.LeaveJob;

public class DeleteGameCommand implements CommandExecutor {
	private final nSpleef plugin;
	public DeleteGameCommand(nSpleef plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (args.length < 2) return false;
	    if (plugin.nSpleefGames.size() == 0){
	    	player.sendMessage(ChatColor.DARK_PURPLE + "No games exist.");
	    	return true;
	    }
		 String name = player.getName();
		 nSpleefGame game = Filter.getGameByName(args[1]);
		 if (game == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That game does not exist.");
			 return true;
		 }
		 if ((name.equalsIgnoreCase(game.getOwner())) || (PermissionHandler.has(player, "nSpleef.admin.deleteanygame"))){
			 nSpleefArena arena = Filter.getArenaByGame(game);
			 for (int i = 0; i < arena.getPlayersIn().size(); i++){
				 new LeaveJob(plugin, arena.getPlayersIn().get(i), 2).run();
			 }
			 if (!(arena.getGame() == null)) plugin.nSpleefGames.remove(arena.getGame());
			 arena.resetVars();
			 player.sendMessage(ChatColor.DARK_PURPLE + "Deleted game.");
		 }else {
			 player.sendMessage(ChatColor.DARK_PURPLE + "You did not create that game!");
		 }
		return true;
	}
}
