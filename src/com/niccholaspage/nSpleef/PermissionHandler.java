package com.niccholaspage.nSpleef;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionHandler {
	private enum PermissionType {
		PERMISSIONS,
		GROUP_MANAGER,
		NONE
	}
	private static PermissionType type;
	private static Plugin permissionPlugin;
	public static void init(Server server){
		Plugin groupManager = server.getPluginManager().getPlugin("GroupManager");
		Plugin permissions = server.getPluginManager().getPlugin("Permissions");
		
		if (groupManager != null){
			permissionPlugin = groupManager;
			type = PermissionType.GROUP_MANAGER;
			System.out.println("[nSpleef] Hooked into Group Manager " + groupManager.getDescription().getVersion());
		}else if (permissions != null){
			permissionPlugin = permissions;
			type = PermissionType.PERMISSIONS;
			System.out.println("[nSpleef] Hooked into Permissions " + permissions.getDescription().getVersion());
		}else {
			type = PermissionType.NONE;
			System.out.println("[nSpleef] Could not find any permissions plugin. Only OPs will be able to use admin commands.");
		}
	}
	public static boolean has(Player player, String node){
		switch (type){
		case PERMISSIONS: return ((Permissions)permissionPlugin).getHandler().has(player, node);
		case GROUP_MANAGER: return ((GroupManager)permissionPlugin).getWorldsHolder().getWorldPermissions(player).has(player, node);
		case NONE: if (node.startsWith("nSpleef.admin")) return player.isOp(); else return true;
		default: return false;
		}
	}
}