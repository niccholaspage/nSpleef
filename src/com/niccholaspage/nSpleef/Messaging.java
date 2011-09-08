package com.niccholaspage.nSpleef;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messaging {
	private static ChatColor color;
	
	public static void setColor(ChatColor configColor){
		color = configColor;
	}
	
	public static void send(CommandSender sender, String message){
		sender.sendMessage(color + message);
	}
}
