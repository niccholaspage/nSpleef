package com.niccholaspage.nSpleef;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.spikensbror.bukkit.mineconomy.MineConomy;

import cosine.boseconomy.BOSEconomy;

public class EconomyHandler {
	public enum EconomyType {
		ICONOMY,
		BOSECONOMY,
		MINECONOMY,
		NONE
	}
	public static EconomyType type;
	private static BOSEconomy bosecon;
	private static MineConomy mineCon;
	
	public static void init(Server server){
		Plugin iconomy = server.getPluginManager().getPlugin("iConomy");
		Plugin boseconomy = server.getPluginManager().getPlugin("BOSEconomy");
		Plugin mineConomy = server.getPluginManager().getPlugin("MineConomy");
		if (iconomy != null){
			type = EconomyType.ICONOMY;
			System.out.println("[nSpleef] Hooked into iConomy " + iconomy.getDescription().getVersion());
		}else if (boseconomy != null){
			type = EconomyType.BOSECONOMY;
			bosecon = (BOSEconomy)boseconomy;
			System.out.println("[nSpleef] Hooked into BOSEconomy " + boseconomy.getDescription().getVersion());
		}else if (mineConomy != null){
			type = EconomyType.MINECONOMY;
			mineCon = (MineConomy)mineConomy;
			System.out.println("[nSpleef] Hooked into MineConomy " + mineConomy.getDescription().getVersion());
		}else {
			type = EconomyType.NONE;
			System.out.println("[nSpleef] No economy plugin found.");
		}
	}
	public static void addMoney(Player player, Integer amount){
		switch (type){
			case ICONOMY: iConomy.getBank().getAccount(player.getName()).add(amount); break;
			case BOSECONOMY: bosecon.addPlayerMoney(player.getName(), amount, false); break;
			case MINECONOMY: mineCon.getBank().add(player.getName(), amount); break;
		}
		}
	public static void removeMoney(Player player, Integer amount){
		switch (type){
		case ICONOMY: iConomy.getBank().getAccount(player.getName()).subtract(amount); break;
		case BOSECONOMY: bosecon.setPlayerMoney(player.getName(), bosecon.getPlayerMoney(player.getName()) - amount, false); break;
		case MINECONOMY: mineCon.getBank().subtract(player.getName(), amount);
		}
	}
	public static Integer getMoney(Player player){
		switch (type){
		case ICONOMY: return (int)iConomy.getBank().getAccount(player.getName()).getBalance();
		case BOSECONOMY: return bosecon.getPlayerMoney(player.getName());
		case MINECONOMY: return (int)mineCon.getBank().getTotal(player.getName());
		default: return null;
		}
	}
	public static String getCurrencyName(){
		switch (type){
		case ICONOMY: return iConomy.getBank().getCurrency() + "s";
		case BOSECONOMY: return bosecon.getMoneyNamePlural();
		case MINECONOMY: return "coins";
		default: return null;
		}
	}
}