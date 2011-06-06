package com.niccholaspage.nSpleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.niccholaspage.nSpleef.Filter;
import com.niccholaspage.nSpleef.PermissionHandler;
import com.niccholaspage.nSpleef.Util;
import com.niccholaspage.nSpleef.Volume;
import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.nSpleefArena;
import com.niccholaspage.nSpleef.nSpleefGame;

public class CreateGameCommand implements CommandExecutor {
	private final nSpleef plugin;
	public CreateGameCommand(nSpleef instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		 if (args.length < 3) return false;
		 if (Util.exists("arenas.txt") == false) {
			 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
			 return true;
		 }
		 if (!(plugin.nSpleefGames.size() == 0)){
		 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
			 if (args[2].equalsIgnoreCase(plugin.nSpleefGames.get(i).getArena())){
				 player.sendMessage(ChatColor.DARK_PURPLE + "A game for that arena already exists.");
				 return true;
			 }
		 }
		 }
		 nSpleefArena arena = Filter.getArenaByName(args[2]);
		 if (arena == null){
			 player.sendMessage(ChatColor.DARK_PURPLE + "That arena does not exist.");
			 return true; 
		 }
		 if (!(Filter.getGameByName(args[1]) == null)){
			 if (args[1].equalsIgnoreCase((Filter.getGameByName(args[1]).getName()))){
				 player.sendMessage(ChatColor.DARK_PURPLE + "A game with that name already exists.");
				 return true;
			 }
		 }
		 if (args[1].contains(",")){
			 player.sendMessage(ChatColor.DARK_PURPLE + "A game name cannot contain a comma.");
			 return true;
		 }
		 arena.createVolume();
		 Volume vol = arena.getVolume();
		 vol.setCornerOne(plugin.getServer().getWorld(arena.getWorld().getName()).getBlockAt(arena.getFirstBlock().getBlockX(), arena.getFirstBlock().getBlockY(), arena.getFirstBlock().getBlockZ()));
		 vol.setCornerTwo(plugin.getServer().getWorld(arena.getWorld().getName()).getBlockAt(arena.getSecondBlock().getBlockX(), arena.getSecondBlock().getBlockY(), arena.getSecondBlock().getBlockZ()));
		 vol.saveBlocks();
		 nSpleefGame game = new nSpleefGame(args[1], args[2].toLowerCase(), player.getName());
		 plugin.nSpleefGames.add(game);
		 if (plugin.method != null && args.length > 3 && plugin.isDouble(args[3])){
			 game.setMoney(Double.parseDouble(args[3]));
		 }
		 player.sendMessage(ChatColor.DARK_PURPLE + "Game " + args[1] + " has been created.");
		 if (args.length > 3){
			 if (PermissionHandler.has(player, "nSpleef.member.wager") && game.getMoney() > 0){
				 player.sendMessage(ChatColor.DARK_PURPLE + "Everyone who joins game " + game.getName() + " must pay " + plugin.method.format(game.getMoney()) + ".");
			 }
		 }
		return true;
	}
}
