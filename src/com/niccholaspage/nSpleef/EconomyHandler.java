package com.niccholaspage.nSpleef;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

public class EconomyHandler {
	public enum EconomyType {
		ICONOMY,
		NONE
	}
	public static EconomyType type;
	
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
	public static void addMoney(Player player, Integer amount){
		if (type.equals(EconomyType.ICONOMY)){
			iConomy.getBank().getAccount(player.getName()).add(amount);
		}
	}
	public static void removeMoney(Player player, Integer amount){
		if (type.equals(EconomyType.ICONOMY)){
			iConomy.getBank().getAccount(player.getName()).subtract(amount);
		}
	}
	public static Integer getMoney(Player player){
		if (type.equals(EconomyType.ICONOMY)){
			return (int)iConomy.getBank().getAccount(player.getName()).getBalance();
		}
		return null;
	}
}