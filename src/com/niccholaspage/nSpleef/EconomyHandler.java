package com.niccholaspage.nSpleef;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
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
	private static Plugin economyPlugin;
	
	public static void init(Server server){
		Plugin iconomy = server.getPluginManager().getPlugin("iConomy");
		Plugin boseconomy = server.getPluginManager().getPlugin("BOSEconomy");
		Plugin mineConomy = server.getPluginManager().getPlugin("MineConomy");
		if (iconomy != null){
			if (iconomy.getDescription().getMain() == "com.nijiko.coelho.iConomy.iConomy"){
				type = EconomyType.NONE;
				System.out.println("[nSpleef] Please upgrade to iConomy 5!");
				return;
			}
			type = EconomyType.ICONOMY;
			System.out.println("[nSpleef] Hooked into iConomy " + iconomy.getDescription().getVersion());
		}else if (boseconomy != null){
			type = EconomyType.BOSECONOMY;
			economyPlugin = boseconomy;
			System.out.println("[nSpleef] Hooked into BOSEconomy " + boseconomy.getDescription().getVersion());
		}else if (mineConomy != null){
			type = EconomyType.MINECONOMY;
			economyPlugin = mineConomy;
			System.out.println("[nSpleef] Hooked into MineConomy " + mineConomy.getDescription().getVersion());
		}else {
			type = EconomyType.NONE;
			System.out.println("[nSpleef] No economy plugin found.");
		}
	}
	public static void addMoney(Player player, Integer amount){
		switch (type){
			case ICONOMY: iConomy.getAccount(player.getName()).getHoldings().add(amount); break;
			case BOSECONOMY: ((BOSEconomy)economyPlugin).addPlayerMoney(player.getName(), amount, false); break;
			case MINECONOMY: ((MineConomy)economyPlugin).getBank().add(player.getName(), amount); break;
		}
		}
	public static void removeMoney(Player player, Integer amount){
		switch (type){
		case ICONOMY: iConomy.getAccount(player.getName()).getHoldings().subtract(amount); break;
		case BOSECONOMY: ((BOSEconomy)economyPlugin).setPlayerMoney(player.getName(), ((BOSEconomy)economyPlugin).getPlayerMoney(player.getName()) - amount, false); break;
		case MINECONOMY: ((MineConomy)economyPlugin).getBank().subtract(player.getName(), amount);
		}
	}
	public static Integer getMoney(Player player){
		switch (type){
		case ICONOMY: return (int)iConomy.getAccount(player.getName()).getHoldings().balance();
		case BOSECONOMY: return ((BOSEconomy)economyPlugin).getPlayerMoney(player.getName());
		case MINECONOMY: return (int)((MineConomy)economyPlugin).getBank().getTotal(player.getName());
		default: return null;
		}
	}
	public static String getCurrencyName(){
		switch (type){
		case ICONOMY: return iConomy.format(0).replace("0 ", "");
		case BOSECONOMY: return ((BOSEconomy)economyPlugin).getMoneyNamePlural();
		case MINECONOMY: return "coins";
		default: return null;
		}
	}
}