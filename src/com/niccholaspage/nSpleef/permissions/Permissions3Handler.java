package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Permissions3Handler implements PermissionsHandler {
	private PermissionHandler p3Handler;
	
	public Permissions3Handler(Plugin plugin){
		p3Handler = ((Permissions) plugin).getHandler();
	}
	
	public boolean has(CommandSender sender, String permission) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return p3Handler.has(player, permission);
		} else {
			return true;
		}
	}
	
	public String getGroup(String name, String world) {
		return p3Handler.getPrimaryGroup(world, name);
	}
	
	public String getPrefix(String name, String world) {
		return p3Handler.getUserPrefix(world, name);
	}
	
	public String getSuffix(String name, String world) {
		return p3Handler.getUserSuffix(world, name);
	}
}
