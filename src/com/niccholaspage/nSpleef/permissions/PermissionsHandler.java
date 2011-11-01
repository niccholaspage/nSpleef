package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;

public interface PermissionsHandler {
	public boolean has(CommandSender sender, String permission);
}
