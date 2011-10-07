package com.niccholaspage.nSpleef;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import com.niccholaspage.nSpleef.command.nSpleefCommand;
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
			
			Map<String, Boolean> adminChildren = new HashMap<String, Boolean>();
			
			Map<String, Boolean> memberChildren = new HashMap<String, Boolean>();
			
			for (nSpleefCommand cmd : plugin.getCommandHandler().getCommands()){
				if (cmd.getPermission().startsWith("nSpleef.member.")){
					memberChildren.put(cmd.getPermission(), true);
				}else {
					adminChildren.put(cmd.getPermission(), true);
				}
			}
			
			Permission admin = new Permission("nSpleef.admin.*", "Admin permissions", adminChildren);
			
			Permission member = new Permission("nSpleef.member.*", "Usual, default permissions", memberChildren);
			
			Map<String, Boolean> allChildren = new HashMap<String, Boolean>();
			
			allChildren.put("nSpleef.admin.*", true);
			
			allChildren.put("nSpleef.member.*", true);
			
			Permission all = new Permission("nSpleef.*", "All permission", allChildren);
			
			plugin.getServer().getPluginManager().addPermission(admin);
			
			plugin.getServer().getPluginManager().addPermission(member);
			
			plugin.getServer().getPluginManager().addPermission(all);
			
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
