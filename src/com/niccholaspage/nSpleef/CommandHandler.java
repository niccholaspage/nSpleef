package com.niccholaspage.nSpleef;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

public class CommandHandler {
	 public static nSpleef plugin;
	  static BlockVector b1loc;
	  static BlockVector b2loc;
	  public CommandHandler(nSpleef instance) {
	        plugin = instance;
	    }
	public void performCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		//TODO: Instead of doing this crap, actually use args
		String[] split = new String[args.length + 1];
		for (int i = 0; i < args.length; i++){
			split[i + 1] = args[i];
		}
		if (!(sender instanceof Player)){
			sender.sendMessage("You are not a player!");
			return;
		}
		Player player = (Player) sender;
			if (split.length >= 2){
			if (split[1].equalsIgnoreCase("define")){
			    if (!nSpleef.Permissions.has(player, "nSpleef.admin.define")) {
			        return;
			    }
			    if (!(split.length == 3)){
			    	player.sendMessage(ChatColor.RED + "/spleef define arenaname");
			    	return;
			    }
				 if (split[2].contains(",")){
					 player.sendMessage(ChatColor.DARK_PURPLE + "An arena name cannot contain a comma.");
					 return;
				 }
			    b1loc = nSpleefBlockListener.b1loc;
			    b2loc = nSpleefBlockListener.b2loc;
				 if ((b1loc == null) || (b2loc == null)){
					 player.sendMessage("Positions aren't set.");
					 return;
				 }
				 for (int i = 0; i<= plugin.nSpleefArenas.size() - 1; i++){
					 if (split[2].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
						 player.sendMessage(ChatColor.DARK_PURPLE + "An arena with that name already exists.");
						 return;
					 }
				 }
				 String name = split[2];
				 Util.openfile();
				 if (b1loc.getBlockY() == b2loc.getBlockY()){
					 player.sendMessage(ChatColor.DARK_PURPLE + "Both blocks cannot be on the same level. If you want a one level spleef arena, make the second point 2 or more blocks below the arena.");
					 return;
				 }
				 if (b1loc.getBlockY() > b2loc.getBlockY()){
					 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + b1loc.getBlockY() + ":" + b1loc.getBlockZ()
							 + ":" + b2loc.getBlockX() + ":" + (b2loc.getBlockY() + 1) + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
				 }else {
					 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + (b1loc.getBlockY() + 1) + ":" + b1loc.getBlockZ()
							 + ":" + b2loc.getBlockX() + ":" + b2loc.getBlockY() + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.world.getName() + "\n");
				 }
				 Util.closefile();
				 Data.setupArrays();
				 player.sendMessage(ChatColor.DARK_PURPLE + "Arena " + name + " has been made!");
				 return;
			}
			if (split[1].equalsIgnoreCase("join")){
			    if (!nSpleef.Permissions.has(player, "nSpleef.member.join")) {
			        return;
			    }
			    if (!(split.length == 3)){
			    	player.sendMessage(ChatColor.RED + "/spleef join gamename");
			    	return;
			    }
			    if (plugin.nSpleefArenas.size() == 0){
			    	return;
			    }
				 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
					 if (plugin.nSpleefArenas.get(i).getPlayersIn().contains(player)){
						 player.sendMessage(ChatColor.DARK_PURPLE + "You are already in a game!");
						 return;
					 }
				 }
				 if (Util.exists() == false) {
					 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");
					 return;
				 }
				 if (plugin.nSpleefGames.size() == 0){
					 player.sendMessage(ChatColor.DARK_PURPLE + "No games!");
					 return;
				 }
				String name = split[2];
			    for (int i = 0; i <= plugin.nSpleefGames.size() - 1; i++) {
			    	if (plugin.nSpleefGames.get(i).split(",")[0].equalsIgnoreCase(name)){
			    		for (int j = 0; j<= plugin.nSpleefArenas.size() - 1; j++){
			    			if (plugin.nSpleefGames.get(i).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(j).getName())){
			    				if (plugin.nSpleefArenas.get(j).getInGame() > 0){
			    					player.sendMessage(ChatColor.DARK_PURPLE + "A game is in progress in that arena.");
			    					return;
			    				}
			    				plugin.nSpleefArenas.get(j).getPlayersLocation().add(player.getLocation().clone());
			    				plugin.nSpleefArenas.get(j).getPlayers().add(player);
			    				plugin.nSpleefArenas.get(j).getPlayersIn().add(player);
			    				plugin.nSpleefArenas.get(j).getPlayerStatus().add(false);
			    				player.teleportTo(plugin.nSpleefArenas.get(j).getTpBlock().toLocation(plugin.nSpleefArenas.get(j).getWorld()));
			    				player.sendMessage(ChatColor.DARK_PURPLE + "Joined game " + name + ".");
			    				player.sendMessage(ChatColor.DARK_PURPLE + "Type ready in the chat when you are ready.");
			    				return;
			    			}
			    		}
			    	}
			    }
			    player.sendMessage(ChatColor.DARK_PURPLE + "The game " + name + " does not exist.");
				return;
			}
			if (split[1].equalsIgnoreCase("leave")){
				plugin.leave(player, true);
			}
			if (split[1].equalsIgnoreCase("list")){
			    if (!nSpleef.Permissions.has(player, "nSpleef.member.list")) {
			        return;
			    }
				 if (Util.exists() == false) {
					 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");
					 return;
				 }
				 player.sendMessage(ChatColor.DARK_PURPLE + "Arenas:");
			    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
			    	player.sendMessage(ChatColor.DARK_PURPLE + plugin.nSpleefArenas.get(i).getName());
			    	}
			    if (!(plugin.nSpleefGames.size() == 0)){
			    	player.sendMessage(ChatColor.DARK_PURPLE + "Games:");
					 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
						 player.sendMessage(ChatColor.DARK_PURPLE + plugin.nSpleefGames.get(i).split(",")[0] + " in arena " + plugin.nSpleefGames.get(i).split(",")[1]);
					 }
			    }else {
			    	player.sendMessage(ChatColor.DARK_PURPLE + "No games.");
			    }
			}
			if (split[1].equalsIgnoreCase("deletegame")){
			    if (!nSpleef.Permissions.has(player, "nSpleef.member.deletegame")) {
			        return;
			    }
			    if (plugin.nSpleefGames.size() == 0){
			    	player.sendMessage(ChatColor.DARK_PURPLE + "No games exist.");
			    	return;
			    }
				 if (!(split.length == 3)){
					 player.sendMessage(ChatColor.RED + "/spleef deletegame namehere");
				     return;
				 }
				 String game = split[2];
				 String name = player.getName();
				 Boolean did = false;
				 Integer v = 0;
				 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
					 if (game.equalsIgnoreCase(plugin.nSpleefGames.get(i).split(",")[0])){
						 did = true;
						 v = i;
					 }
				 }
				 if (did == false){
					 player.sendMessage(ChatColor.DARK_PURPLE + "That game does not exist.");
					 return;
				 }
				 if ((name.equalsIgnoreCase(plugin.nSpleefGames.get(v).split(",")[2])) || (nSpleef.Permissions.has(player, "nSpleef.admin.deleteanygame"))){
					 for (int i = 0; i<=plugin.nSpleefArenas.size() - 1; i++){
					 if (plugin.nSpleefGames.get(v).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
						 plugin.nSpleefArenas.get(i).resetVars();
					 }
					 }
					 plugin.nSpleefGames.remove(v.intValue());
					 player.sendMessage(ChatColor.DARK_PURPLE + "Deleted game.");
				 }else {
					 player.sendMessage(ChatColor.DARK_PURPLE + "You did not create that game!");
				 }
			}
			if (split[1].equalsIgnoreCase("creategame")){
			    if (!nSpleef.Permissions.has(player, "nSpleef.member.creategame")) {
			        return;
			    }
				 if (Util.exists() == false) {
					 player.sendMessage(ChatColor.DARK_PURPLE + "No arenas!");						 
					 return;
				 }
				 if (!(split.length == 4)){
					 player.sendMessage(ChatColor.RED + "/spleef creategame gamename arena");
				     return;
				 }
				 if (!(plugin.nSpleefGames.size() == 0)){
				 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
					 if (split[3].equalsIgnoreCase(plugin.nSpleefGames.get(i).split(",")[1])){
						 player.sendMessage(ChatColor.DARK_PURPLE + "A game for that arena already exists.");
						 return;
					 }
				 }
				 }
				 String name = split[2] + "," + split[3].toLowerCase() + "," + player.getName();
				 Boolean passed = false;
				 Integer where = 0;
				 for (int i = 0; i<= plugin.nSpleefArenas.size() - 1; i++){
				 if (split[3].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
					 passed = true;
					 where = i;
				 }
				 }
				 if (passed == false){
					 player.sendMessage(ChatColor.DARK_PURPLE + "The arena does not exist.");
					 return;
				 }
				 if (!(plugin.nSpleefGames.size() == 0)){
				 for (int i = 0; i<= plugin.nSpleefGames.size() - 1; i++){
					 if (split[2].equalsIgnoreCase((plugin.nSpleefGames.get(i).split(",")[0]))){
						 player.sendMessage(ChatColor.DARK_PURPLE + "A game with that name already exists.");
						 return;
					 }
				 }
				 }
				 if (split[2].contains(",")){
					 player.sendMessage(ChatColor.DARK_PURPLE + "A game name cannot contain a comma.");
					 return;
				 }
				 plugin.nSpleefArenas.get(where).createVolume();
				 Volume vol = plugin.nSpleefArenas.get(where).getVolume();
				 vol.setCornerOne(plugin.getServer().getWorld(plugin.nSpleefArenas.get(where).getWorld().getName()).getBlockAt(plugin.nSpleefArenas.get(where).getFirstBlock().getBlockX(), plugin.nSpleefArenas.get(where).getFirstBlock().getBlockY(), plugin.nSpleefArenas.get(where).getFirstBlock().getBlockZ()));
				 vol.setCornerTwo(plugin.getServer().getWorld(plugin.nSpleefArenas.get(where).getWorld().getName()).getBlockAt(plugin.nSpleefArenas.get(where).getSecondBlock().getBlockX(), plugin.nSpleefArenas.get(where).getSecondBlock().getBlockY(), plugin.nSpleefArenas.get(where).getSecondBlock().getBlockZ()));
				 vol.saveBlocks();
				 plugin.nSpleefGames.add(name);
				 plugin.nSpleefArenas.get(where).setMyGame(plugin.nSpleefGames.size());
				 player.sendMessage(ChatColor.DARK_PURPLE + "Game " + split[2] + " has been created.");
			}
			}
	}
}
