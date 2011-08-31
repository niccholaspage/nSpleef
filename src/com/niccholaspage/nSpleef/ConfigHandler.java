package com.niccholaspage.nSpleef;

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
		
		config.save();
		
		item = config.getInt("item", 280);
	}
	
	private void writeNode(String node, Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
	
	public int getItem(){
		return item;
	}
}
