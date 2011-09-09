package com.niccholaspage.nSpleef;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

public class ConfigHandler {
	private Configuration config;
	
	private int item;
	
	private Map<String, ChatColor> chatColors = new HashMap<String, ChatColor>();
	
	public ConfigHandler(Configuration config){
		this.config = config;
		
		for (ChatColor color : ChatColor.values()){
			chatColors.put(getGoodName(color), color);
		}
		
		load();
	}
	
	private String getGoodName(ChatColor color){
		return color.name().toLowerCase().replace("_", "");
	}
	
	public void load(){
		config.load();
		
		writeNode("item", 280, config);
		
		writeNode("messagecolor", getGoodName(ChatColor.DARK_PURPLE), config);
		
		config.save();
		
		item = config.getInt("item", 280);
		
		ChatColor color = ChatColor.DARK_PURPLE;
		
		String readColor = config.getString("messagecolor", getGoodName(ChatColor.DARK_PURPLE)).toLowerCase();
		
		if (chatColors.containsKey(readColor)){
			color = chatColors.get(readColor);
		}
		
		Messaging.setColor(color);
	}
	
	private void writeNode(String node, Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
	
	public int getItem(){
		return item;
	}
}
