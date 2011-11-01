package com.niccholaspage.nSpleef.permissions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.niccholaspage.nSpleef.nSpleef;
import com.niccholaspage.nSpleef.command.nSpleefCommand;

public class DinnerPermissionsHandler implements PermissionsHandler {
	public DinnerPermissionsHandler(nSpleef plugin){
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
	}
	
	public boolean has(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
}