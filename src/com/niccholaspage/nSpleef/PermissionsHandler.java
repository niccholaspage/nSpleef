package com.niccholaspage.nSpleef;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionsHandler {
	private final Plugin plugin;
	
	private final PermissionsType type;
	
	private enum PermissionsType {
		PERMISSIONS,
		BUKKIT_PERMS,
	}
	
	public PermissionsHandler(nSpleef plugin){
		Plugin permissions = plugin.getServer().getPluginManager().getPlugin("Permissions");
		
		if (permissions != null){
			type = PermissionsType.PERMISSIONS;
			
			this.plugin = permissions;
			
			plugin.log("Hooked into Permissions " + permissions.getDescription().getVersion());
		}else {
			type = PermissionsType.BUKKIT_PERMS;
			
			this.plugin = null;
			
			plugin.log("Hooked into Bukkit Permissions");
		}
	}
	
	public boolean has(CommandSender sender, String node){
		if (!(sender instanceof Player)){
			return true;
		}
		
		Player player = (Player) sender;
		
		switch (type){
		case PERMISSIONS: return ((Permissions) plugin).getHandler().has(player, node);
		case BUKKIT_PERMS: return player.hasPermission(node);
		default: return false;
		}
	}
}
