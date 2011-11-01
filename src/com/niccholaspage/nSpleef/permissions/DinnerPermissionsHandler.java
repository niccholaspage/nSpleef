package com.niccholaspage.nSpleef.permissions;

import org.bukkit.command.CommandSender;

public class DinnerPermissionsHandler implements PermissionsHandler {
	public boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
}