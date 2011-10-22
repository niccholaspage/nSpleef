package com.niccholaspage.nSpleef;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	private File configFile;
	
	private YamlConfiguration config;
	
	private int item;
	
	private Map<String, ChatColor> chatColors = new HashMap<String, ChatColor>();
	
	public ConfigHandler(File configFile){
		this.configFile = configFile;
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
		
		for (ChatColor color : ChatColor.values()){
			chatColors.put(getGoodName(color), color);
		}
		
		load();
	}
	
	private String getGoodName(ChatColor color){
		return color.name().toLowerCase().replace("_", "");
	}
	
	public void load(){
		ChatColor def = ChatColor.DARK_PURPLE;
		
		config.setDefaults(getDefaultConfig(def));
		
		config.options().copyDefaults(true);
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		item = config.getInt("item", 280);
		
		ChatColor color = def;
		
		String readColor = config.getString("messagecolor", getGoodName(def)).toLowerCase();
		
		if (chatColors.containsKey(readColor)){
			color = chatColors.get(readColor);
		}
		
		Messaging.setColor(color);
	}
	
	private YamlConfiguration getDefaultConfig(ChatColor defaultColor){
		YamlConfiguration defaultConfig = new YamlConfiguration();
		
		defaultConfig.set("item", 280);
		
		defaultConfig.set("messagecolor", getGoodName(defaultColor));
		
		return defaultConfig;
	}
	
	public int getItem(){
		return item;
	}
}
