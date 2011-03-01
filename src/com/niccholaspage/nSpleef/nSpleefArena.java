package com.niccholaspage.nSpleef;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

public class nSpleefArena {
	private final String name;
	private World world;
	private final ArrayList<Player> players = new ArrayList<Player>();
	//Dirty workaround :)
	private ArrayList<Boolean> playerstatus = new ArrayList<Boolean>();
	private BlockVector block1;
	private BlockVector block2;
	private BlockVector tpblock = new BlockVector(0,0,0);
	private Volume vol = null;
	private Boolean ingame = false;
	private Integer mygame = null;
	 public static nSpleef plugin;
	  public nSpleefArena(String name, World world){
		  this.name = name;
		  this.world = world;
	  }
	  public String getName(){
		  return this.name;
	  }
	  public Integer getMyGame(){
		  return this.mygame;
	  }
	  public void setMyGame(Integer game){
		  this.mygame = game;
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
				 tpblock.setY(block1.getBlockY());
			 }else {
				 tpblock.setY(block2.getBlockY());
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
	  public ArrayList<Player> getPlayers(){
		  return players;
	  }
	  public ArrayList<Boolean> getPlayerStatus(){
		  return playerstatus;
	  }
	  public Volume getVolume(){
		  return this.vol;
	  }
	public void setInGame(Boolean ingame) {
		this.ingame = ingame;
	}
	public void go(){
		Runnable r = new Runnable(){
			public void run(){
				for (int i = 0; i <= players.size() - 1; i++){
					players.get(i).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] 3");
				}
				Util.waitMS(1000);
				for (int i = 0; i <= players.size() - 1; i++){
					players.get(i).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] 2");
				}
				Util.waitMS(1000);
				for (int i = 0; i <= players.size() - 1; i++){
					players.get(i).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] 1");
				}
				Util.waitMS(1000);
				for (int i = 0; i <= players.size() - 1; i++){
					players.get(i).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] Go!");
				}
				ingame = true;
			}
		};
		new Thread(r).start();
	}
	public void checkReady(){
		if (players.size() == 1){
			return;
		}
		for (int i = 0; i<= playerstatus.size() - 1; i++){
			if (playerstatus.get(i) == false){
				return;
			}
		}
		go();
	}
	public void leave(Player player){
		players.remove(player);
		player.teleportTo(player.getWorld().getSpawnLocation());
		player.sendMessage(ChatColor.DARK_PURPLE + "You've left the spleef game.");
		if (players.size() == 1){
			ingame = false;
			players.get(0).sendMessage(ChatColor.DARK_PURPLE + "Everyone else has left. If you would like to leave, type /spleef leave.");
			return;
		}
		if (players.size() == 0){
			ingame = false;
			vol.resetBlocks();
			this.playerstatus = new ArrayList<Boolean>();
		}
	}
	public void checkLeave(){
		if (players.size() == 1){
			ingame = false;
			players.get(0).sendMessage(ChatColor.DARK_PURPLE + "[nSpleef] You have won the game!");
			players.get(0).teleportTo(players.get(0).getWorld().getSpawnLocation());
			players.remove(0);
			vol.resetBlocks();
			return;
		}
		if (players.size() == 0){
			ingame = false;
			vol.resetBlocks();
			this.playerstatus = new ArrayList<Boolean>();
		}
	}
	public Boolean getInGame() {
		return ingame;
	}
}