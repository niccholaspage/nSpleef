package com.niccholaspage.nSpleef;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

import cosine.boseconomy.BOSEconomy;

public class EconomyHandler {
	public enum EconomyType {
		ICONOMY,
		BOSECONOMY,
		NONE
	}
	public static EconomyType type;
	private static Plugin economyPlugin;
	private static BOSEconomy bosecon;
	
	public static void init(Server server){
		Plugin iconomy = server.getPluginManager().getPlugin("iConomy");
		Plugin boseconomy = server.getPluginManager().getPlugin("BOSEconomy");
		if (iconomy != null){
			type = EconomyType.ICONOMY;
			economyPlugin = iconomy;
			System.out.println("[nSpleef] Hooked into iConomy " + iconomy.getDescription().getVersion());
		}else if (boseconomy != null){
			type = EconomyType.BOSECONOMY;
			economyPlugin = boseconomy;
			bosecon = (BOSEconomy)economyPlugin;
			System.out.println("[nSpleef] Hooked into BOSEconomy " + boseconomy.getDescription().getVersion());
		}else {
			type = EconomyType.NONE;
			System.out.println("[nSpleef] No economy plugin found.");
		}
	}
	public static void addMoney(Player player, Integer amount){
		switch (type){
			case ICONOMY: iConomy.getBank().getAccount(player.getName()).add(amount); break;
			case BOSECONOMY:
				bosecon.addPlayerMoney(player.getName(), amount, false);
				break;
				}
		}
	public static void removeMoney(Player player, Integer amount){
		switch (type){
		case ICONOMY: iConomy.getBank().getAccount(player.getName()).subtract(amount); break;
		case BOSECONOMY: bosecon.setPlayerMoney(player.getName(), bosecon.getPlayerMoney(player.getName()) - amount, false); break;
		}
	}
	public static Integer getMoney(Player player){
		switch (type){
		case ICONOMY: return (int)iConomy.getBank().getAccount(player.getName()).getBalance();
		case BOSECONOMY: return bosecon.getPlayerMoney(player.getName());
		default: return null;
		}
	}
	public static String getCurrencyName(){
		switch (type){
		case ICONOMY: return iConomy.getBank().getCurrency() + "s";
		case BOSECONOMY: return bosecon.getMoneyNamePlural();
		default: return null;
		}
	}
}