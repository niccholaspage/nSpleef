package com.niccholaspage.nSpleef;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

public class EconomyHandler {
	private enum EconomyType {
		ICONOMY,
		NONE
	}
	private static EconomyType type;
	
	public static void init(Server server){
		Plugin iconomy = server.getPluginManager().getPlugin("iConomy");
		if (iconomy != null){
			type = EconomyType.ICONOMY;
			System.out.println("[nSpleef] Hooked into iConomy " + iconomy.getDescription().getVersion());
		}else {
			type = EconomyType.NONE;
			System.out.println("[nSpleef] No economy plugin found.");
		}
	}
	public static void addMoney(Player player, Double amount){
		if (type.equals(EconomyType.ICONOMY)){
			iConomy.getBank().getAccount(player.getName()).add(amount);
		}
	}
	public static void removeMoney(Player player, Double amount){
		if (type.equals(EconomyType.ICONOMY)){
			iConomy.getBank().getAccount(player.getName()).subtract(amount);
		}
	}
	public static Double getMoney(Player player){
		if (type.equals(EconomyType.ICONOMY)){
			return iConomy.getBank().getAccount(player.getName()).getBalance();
		}
		return null;
	}
}