package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExHandler implements PermissionsHandler {
	private final PermissionManager pexHandler;
	
	public PermissionsExHandler(){
		this.pexHandler = PermissionsEx.getPermissionManager();
	}
	
	public boolean has(CommandSender sender, String permission){
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return pexHandler.has(player, permission);
		} else {
			return true;
		}
	}
	
	public String getGroup(String name, String world){
		return pexHandler.getUser(name).getGroups(world)[0].getName();
	}
	
	public String getPrefix(String name, String world){
		return pexHandler.getUser(name).getOption("prefix");
	}
	
	public String getSuffix(String name, String world){
		return pexHandler.getUser(name).getOption("suffix");
	}
}
