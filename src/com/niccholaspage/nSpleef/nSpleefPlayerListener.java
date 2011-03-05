//The Package
package com.niccholaspage.nSpleef;
//All the imports
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockVector;
import com.niccholaspage.nSpleef.Util;
//Starts the class nSpleefPlayer listener
public class nSpleefPlayerListener extends PlayerListener{
	 public static nSpleef plugin;
	  public nSpleefPlayerListener(nSpleef instance) {
	        plugin = instance;
	    }
	  static BlockVector b1loc;
	  static BlockVector b2loc;
		 public void onPlayerMove(PlayerMoveEvent event){
			 Player player = event.getPlayer();
			 Location loc = player.getLocation();
			 Boolean pass = false;
			 if (plugin.nSpleefArenas.size() == 0){
				 return;
			 }
			 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
					 pass = true;
				 }
			 }
			 if (pass == false){
				 return;
			 }
			 player.setHealth(20);
				 BlockVector theblock = new BlockVector();
				    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++) {
						 if ((nSpleefBlockListener.returnblockinarea(Util.toVector(loc.getBlock()), plugin.nSpleefArenas.get(i).getFirstBlock(), plugin.nSpleefArenas.get(i).getSecondBlock())) == true) {
								 if (player.getWorld().toString().equals(plugin.nSpleefArenas.get(i).getWorld().toString())){
									 Boolean con = false;
									 for (int j = 0; j<= plugin.nSpleefGames.size() - 1; j++){
										 if (plugin.nSpleefGames.get(j).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(i).getName())){
											 con = true;
											 break;
										 }
									 }
									 if (con == false){
										 return;
									 }
									 if (plugin.nSpleefArenas.get(i).getFirstBlock().getBlockY() == plugin.nSpleefArenas.get(i).getSecondBlock().getBlockY()){
										 return;
									 }
									 if (plugin.nSpleefArenas.get(i).getFirstBlock().getBlockY() > plugin.nSpleefArenas.get(i).getSecondBlock().getBlockY()){
										 theblock = plugin.nSpleefArenas.get(i).getSecondBlock();
									 }
									 if (plugin.nSpleefArenas.get(i).getFirstBlock().getBlockY() < plugin.nSpleefArenas.get(i).getSecondBlock().getBlockY()){
										 theblock = plugin.nSpleefArenas.get(i).getFirstBlock();
									 } 
										 if (loc.getBlockY() + 1 == theblock.getBlockY() + 1){
											 for (int k = 0; k <= plugin.nSpleefArenas.size() - 1; k++){
												 if (plugin.nSpleefArenas.get(k).getPlayers().contains(player)){
													 for (int j = 0; j <= plugin.nSpleefArenas.get(k).getPlayers().size() - 1; j++){
														 plugin.nSpleefArenas.get(k).getPlayersIn().get(j).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] " + player.getDisplayName() + " is out!");
													 }
													 plugin.nSpleefArenas.get(k).getPlayers().remove(player);
													 plugin.nSpleefArenas.get(k).checkLeave();
													 return;
												 }
											 }
										 }
								 }
				    	}
				    }
		 }
		 public void onPlayerChat(PlayerChatEvent event){
			 
			 Player player = event.getPlayer();
			 
			 if (plugin.nSpleefGames.size() == 0){
				 return;
			 }
			 if (event.getMessage().toLowerCase().contains("ready")){
				 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
					 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
						 if (plugin.nSpleefArenas.get(i).getInGame() == 0){
							 Integer where = plugin.nSpleefArenas.get(i).getPlayers().indexOf(player);
							 plugin.nSpleefArenas.get(i).getPlayerStatus().set(where, true);
							 plugin.nSpleefArenas.get(i).checkReady();
						 }
					 }
				 }
			 }
		 }
		 public void onPlayerCommandPreprocess(PlayerChatEvent event) {
		  //Make the message a string.
			String[] split = event.getMessage().split(" ");
			//Get the player that talked.
			Player player = event.getPlayer();
			//If the first part of the string is /nSpleef or /s then do this.
			if (split[0].equalsIgnoreCase("/spleef")){
				if (split.length >= 2){
				if (split[1].equalsIgnoreCase("define")){
				    if (!nSpleef.Permissions.has(player, "nSpleef.admin")) {
				        return;
				    }
				    if (!(split.length == 3)){
				    	player.sendMessage(ChatColor.RED + "/spleef define arenaname");
				    	return;
				    }
				    if (!(plugin.nSpleefArenas.size() == 0)){
				    }
				    b1loc = nSpleefBlockListener.returnblock(1);
				    b2loc = nSpleefBlockListener.returnblock(2);
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
						 return;
					 }
					 if (b1loc.getBlockY() > b2loc.getBlockY()){
						 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + b1loc.getBlockY() + ":" + b1loc.getBlockZ()
								 + ":" + b2loc.getBlockX() + ":" + (b2loc.getBlockY() + 1) + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.returnworld().getName() + "\n");
					 }else {
						 Util.writefile(name + ":" + b1loc.getBlockX() + ":" + (b1loc.getBlockY() + 1) + ":" + b1loc.getBlockZ()
								 + ":" + b2loc.getBlockX() + ":" + b2loc.getBlockY() + ":" + b2loc.getBlockZ() + ":" + nSpleefBlockListener.returnworld().getName() + "\n");
					 }
					 Util.closefile();
					 Data.setupArrays();
					 player.sendMessage(ChatColor.DARK_PURPLE + "Arena " + name + " has been made!");
					 return;
				}
				if (split[1].equalsIgnoreCase("join")){
				    if (!nSpleef.Permissions.has(player, "nSpleef.member")) {
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
						 if (plugin.nSpleefArenas.get(i).getPlayers().contains(player)){
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
				    	if ((plugin.nSpleefGames.get(i).split(",")[0].equalsIgnoreCase(name)) && (!(plugin.enabled(player)))){
				    		for (int j = 0; j<= plugin.nSpleefArenas.size() - 1; j++){
				    			if (plugin.nSpleefGames.get(i).split(",")[1].equalsIgnoreCase(plugin.nSpleefArenas.get(j).getName())){
				    				if (plugin.nSpleefArenas.get(j).getInGame() > 0){
				    					player.sendMessage(ChatColor.DARK_PURPLE + "A game is in progress in that arena.");
				    					return;
				    				}
				    				player.teleportTo(plugin.nSpleefArenas.get(j).getTpBlock().toLocation(plugin.nSpleefArenas.get(j).getWorld()));
				    				plugin.nSpleefArenas.get(j).getPlayers().add(player);
				    				plugin.nSpleefArenas.get(j).getPlayersIn().add(player);
				    				plugin.nSpleefArenas.get(j).getPlayerStatus().add(false);
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
				/*if (split[1].equalsIgnoreCase("deletearena")){
				    if (!nSpleef.Permissions.has(player, "nSpleef.admin")) {
				        return;
				    }
				    if (!(split.length == 3)){
				    	player.sendMessage(ChatColor.RED + "/spleef deletearena arenaname");
				    	return;
				    }
				    if (plugin.nSpleefArenas.size() == 0){
				    	player.sendMessage(ChatColor.DARK_PURPLE + "No arenas exist.");
				    	return;
				    }
				    for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
				    	if (plugin.nSpleefArenas.get(i).getName().equalsIgnoreCase(split[2])){
				    		nSpleefArena pointer = plugin.nSpleefArenas.get(i);
				    		BlockVector pointerb1 = pointer.getFirstBlock();
				    		BlockVector pointerb2 = pointer.getSecondBlock();
				    		String text = pointer.getName() + ":" + pointerb1.getBlockX() + ":" + pointerb1.getBlockY() + ":" + pointerb1.getBlockZ() + ":" + pointerb2.getBlockX() + ":" + pointerb2.getBlockY() + ":" + pointerb2.getBlockZ() + ":" + pointer.getWorld() + "\n";
				    		Util.openfileread();
				    		ArrayList<String> file = Util.filetoarray();
				    		Util.closefileread();
				    		player.sendMessage("" + file.size());
				    		for (int j = 0; j<=file.size() - 1; j++){
				    			if (file.get(j).equalsIgnoreCase(text)){
				    				file.remove(j);
				    			}
				    		}
				    		Util.openfile();
				    		Util.replaceFile(file);
				    		Util.closefile();
				    		Data.setupArrays();
				    	}
				    }
				}*/
				if (split[1].equalsIgnoreCase("leave")){
				    if (!nSpleef.Permissions.has(player, "nSpleef.member")) {
				        return;
				    }
				    if (plugin.nSpleefArenas.size() == 0){
				    	return;
				    }
					 for (int i = 0; i <= plugin.nSpleefArenas.size() - 1; i++){
						 for (int j = 0; j <= plugin.nSpleefArenas.get(i).getPlayers().size() - 1; j++){
							 if (player.equals(plugin.nSpleefArenas.get(i).getPlayers().get(j))){
								 	plugin.nSpleefArenas.get(i).getPlayerStatus().remove(j);
								 	plugin.nSpleefArenas.get(i).getPlayersIn().remove(j);
									plugin.nSpleefArenas.get(i).leave(player);
							 }
						 }
					 }
				}
				if (split[1].equalsIgnoreCase("list")){
				    if (!nSpleef.Permissions.has(player, "nSpleef.member")) {
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
				    if (!nSpleef.Permissions.has(player, "nSpleef.member")) {
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
					 if ((name.equalsIgnoreCase(plugin.nSpleefGames.get(v).split(",")[2])) || (nSpleef.Permissions.has(player, "nSpleef.admin"))){
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
				    if (!nSpleef.Permissions.has(player, "nSpleef.member")) {
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
}