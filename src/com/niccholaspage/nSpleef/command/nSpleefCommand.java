package com.niccholaspage.nSpleef.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nSpleef.nSpleef;

public class nSpleefCommand {
	protected final nSpleef plugin;
	
	private String name;
	
	private String help;
	
	private String permission = "";
	
	private boolean consoleCommand;
	
	public nSpleefCommand(nSpleef plugin){
		this.plugin = plugin;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getHelp(){
		return help;
	}
	
	public void setHelp(String help){
		this.help = help;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public void setPermission(String permission){
		this.permission = permission;
	}
	
	public void setConsoleCommand(boolean consoleCommand){
		this.consoleCommand = consoleCommand;
	}
	
	public boolean isConsoleCommand(){
		return consoleCommand;
	}
	
	public boolean run(CommandSender sender, Command cmd, String[] args){
		return true;
	}
}
