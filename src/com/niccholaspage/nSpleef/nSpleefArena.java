package com.niccholaspage.nSpleef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import com.niccholaspage.nSpleef.jobs.LeaveJob;

public class nSpleefArena {
	private final nSpleef plugin;
	private final String name;
	private World world;
	private List<Player> players = new ArrayList<Player>();
	private List<Player> playersin = new ArrayList<Player>();
	private List<Boolean> playerstatus = new ArrayList<Boolean>();
	private List<Location> playerslocation = new ArrayList<Location>();
	private BlockVector block1;
	private BlockVector block2;
	private BlockVector tpblock = new BlockVector(0,0,0);
	private Volume vol = null;
	private Integer ingame = 0;
	private boolean gracePeriod = false;
	public nSpleefArena(String name, World world, nSpleef plugin){
		this.name = name;
		this.world = world;
		this.plugin = plugin;
	}
	public String getName(){
		return this.name;
	}
	public World getWorld(){
		return this.world;
	}
	public void setFirstBlock(BlockVector b1){
		this.block1 = b1;
	}
	public void setSecondBlock(BlockVector b2){
		this.block2 = b2;
	}
	public void setTpBlock(){
		if (block1.getBlockX() == block2.getBlockX()){
			this.tpblock = block1;
			return;
		}
		if (block1.getBlockX() > block2.getBlockX()){
			tpblock.setX(block1.getBlockX() - block2.getBlockX() + block2.getBlockX());
		}
		if (block1.getBlockX() < block2.getBlockX()){
			tpblock.setX(block2.getBlockX() - block1.getBlockX() + block1.getBlockX());
		}
		if (block1.getBlockZ() > block2.getBlockZ()){
			tpblock.setZ(block1.getBlockZ() - block2.getBlockZ() + block2.getBlockZ());
		}
		if (block1.getBlockZ() < block2.getBlockZ()){
			tpblock.setZ(block2.getBlockZ() - block1.getBlockZ() + block1.getBlockZ());
		}
		if (block1.getBlockY() > block2.getBlockY()){
			tpblock.setY(block1.getBlockY() + 1);
		}else {
			tpblock.setY(block2.getBlockY() + 1);
		}
	}
	public BlockVector getTpBlock(){
		return this.tpblock;
	}
	public void createVolume(){
		this.vol = new Volume(name,world);
	}
	public BlockVector getFirstBlock(){
		return this.block1;
	}
	public BlockVector getSecondBlock(){
		return this.block2;
	}
	public List<Player> getPlayers(){
		return players;
	}
	public List<Player> getPlayersIn(){
		return playersin;
	}
	public List<Boolean> getPlayerStatus(){
		return playerstatus;
	}
	public List<Location> getPlayersLocation(){
		return playerslocation;
	}
	public Volume getVolume(){
		return this.vol;
	}
	public void setInGame(Integer ingame) {
		this.ingame = ingame;
	}
	public boolean isGracePeriod(){
		return gracePeriod;
	}
	public void go(){
		ingame = 1;
		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
			public void run(){
				for (int i = 3; i > 0; i--){
					messagePlayersIn(ChatColor.DARK_PURPLE + "[nSpleef] " + i);
					Util.waitMS(1000);
				}
				messagePlayersIn(ChatColor.DARK_PURPLE + "[nSpleef] Go!");
				ingame = 2;
			}
		});
		if (getGame().getMode() == 1){//1 is Thunder
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
				public void run(){
					Util.waitMS(4000);
					while (ingame == 2){
						Util.waitMS(3000);
						world.strikeLightning(players.get(new Random().nextInt(players.size())).getLocation());
					}
				}
			});
		}
	}
	public void checkReady(){
		if (players.size() == 1) return;
		for (int i = 0; i<= playerstatus.size() - 1; i++){
			if (playerstatus.get(i) == false){
				return;
			}
		}
		go();
	}
	public void join(final Player player){
		getPlayersLocation().add(player.getLocation().clone());
		getPlayers().add(player);
		getPlayersIn().add(player);
		getPlayerStatus().add(false);
		player.teleport(getTpBlock().toLocation(getWorld()));
		if (!(plugin.joinKickerTime == 0)){
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
				public void run(){
					Util.waitMS(plugin.joinKickerTime * 1000);
					if (Filter.getArenaByPlayer(player) == null) return;
					if (getPlayerStatus().get(getPlayers().indexOf(player)) == false){
						new LeaveJob(plugin, player, 3).run();
					}
				}
			});
		}
	}
	public void leave(){
		if (players.size() == 1){
			ingame = 0;
			players.get(0).sendMessage(ChatColor.DARK_PURPLE + "Everyone else has left. If you would like to leave, type /spleef leave.");
			return;
		}
		if (players.size() == 0){
			ingame = 0;
			vol.resetBlocks();
			resetVars();
		}
	}
	public void checkLeave(){
		if (players.size() == 1){
			ingame = 0;
			messagePlayersIn(ChatColor.DARK_PURPLE + "[nSpleef] " + players.get(0).getDisplayName() + ChatColor.DARK_PURPLE + " has won the game!");
			if (getGame().getMoney() > 0 && plugin.method != null){
				double amount = getGame().getMoney() * playersin.size();
				plugin.method.getAccount(players.get(0).getName()).add(amount);
				players.get(0).sendMessage(ChatColor.DARK_PURPLE + "You just won " + plugin.method.format(amount) + ".");
			}
			gracePeriod = true;
			players.remove(0);
			checkLeave();
			return;
		}
		if (players.size() == 0){
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					ingame = 0;
					for (int i = 0; i < playersin.size(); i++){
						playersin.get(i).teleport(playerslocation.get(i));
					}
					vol.resetBlocks();
					resetVars();
				}
			}, 20);
		}
	}
	public void resetVars(){
		players = new ArrayList<Player>();
		playersin = new ArrayList<Player>();
		playerstatus = new ArrayList<Boolean>();
		playerslocation = new ArrayList<Location>();
		gracePeriod = false;
	}
	public Integer getInGame() {
		return ingame;
	}
	public nSpleefGame getGame(){
		for (int i = 0; i < plugin.nSpleefGames.size(); i++){
			if (plugin.nSpleefGames.get(i).getArena().equalsIgnoreCase(name)){
				return plugin.nSpleefGames.get(i);
			}
		}
		return null;
	}
	public void messagePlayersIn(String message){
		for (int i = 0; i < playersin.size(); i++){
			playersin.get(i).sendMessage(message);
		}
	}
}