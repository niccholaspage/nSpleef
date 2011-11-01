package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;

public interface PermissionsHandler {
	public boolean hasPermission(CommandSender sender, String permission);
}
