package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Permissions2Handler implements PermissionsHandler {
	private PermissionHandler p2Handler;
	
	public Permissions2Handler(Plugin plugin){
		p2Handler = ((Permissions) plugin).getHandler();
	}
	
	public boolean has(CommandSender sender, String permission) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return p2Handler.has(player, permission);
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public String getGroup(String name, String world){
		return p2Handler.getGroup(world, name);
	}
	
	@SuppressWarnings("deprecation")
	public String getPrefix(String name, String world){
		String group = getGroup(name, world);
		
		String prefix = p2Handler.getGroupPrefix(world, group);
		
		String userPrefix = p2Handler.getPermissionString(world, name, "prefix");
		
		if (userPrefix != null) prefix = userPrefix;
		
		if (prefix == null) prefix = "";
		
		return prefix;
	}
	
	@SuppressWarnings("deprecation")
	public String getSuffix(String name, String world){
		String group = getGroup(name, world);
		String suffix = p2Handler.getGroupSuffix(world, group);
		
		String userSuffix = p2Handler.getPermissionString(world, name, "suffix");
		
		if (userSuffix != null) suffix = userSuffix;
		
		if (suffix == null) suffix = "";
		
		return suffix;
	}
}
