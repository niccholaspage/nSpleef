package com.niccholaspage.nSpleef;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

public class ConfigHandler {
	private Configuration config;
	
	private int item;
	
	public ConfigHandler(Configuration config){
		this.config = config;
		
		load();
	}
	
	public void load(){
		config.load();
		
		writeNode("item", 280, config);
		
		writeNode("messagecolor", ChatColor.DARK_PURPLE.getCode(), config);
		
		config.save();
		
		item = config.getInt("item", 280);
		
		int code = config.getInt("messagecolor", ChatColor.DARK_PURPLE.getCode());
		
		ChatColor color = ChatColor.getByCode(code);
		
		Messaging.setColor(color == null ? ChatColor.DARK_PURPLE : color);
	}
	
	private void writeNode(String node, Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
	
	public int getItem(){
		return item;
	}
}
