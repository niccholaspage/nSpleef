package com.niccholaspage.nSpleef;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messaging {
	private static ChatColor color;
	
	public static void setColor(ChatColor configColor){
		color = configColor;
	}
	
	public static void sendWithPrefix(CommandSender sender, String message, String... params){
		send(sender, "[nSpleef] " + message, params);
	}
	
	public static void send(CommandSender sender, String message, String... params){
		if (params != null){
			for (int i = 0; i < params.length; i++){
				message = message.replace("$" + (i + 1), params[i]);
			}
		}
		
		sender.sendMessage(color + message);
	}
}
