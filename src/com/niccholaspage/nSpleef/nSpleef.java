package com.niccholaspage.nSpleef;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nSpleef.command.CommandHandler;
import com.niccholaspage.nSpleef.command.commands.*;
import com.niccholaspage.nSpleef.listeners.nSpleefPlayerListener;
import com.niccholaspage.nSpleef.player.Session;

public class nSpleef extends JavaPlugin {
	private Logger log;
	
	private Set<Session> sessions;
	
	private Set<nSpleefArena> arenas;
	
	private ConfigHandler configHandler;
	
	private PermissionsHandler permissionsHandler;

	@Override
	public void onDisable() {
		log("Disabled!");
	}

	@Override
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		
		arenas = new HashSet<nSpleefArena>();
		
		sessions = new HashSet<Session>();
		
		PluginManager pluginManager = getServer().getPluginManager();
		
		nSpleefPlayerListener playerListener = new nSpleefPlayerListener(this);
		
		pluginManager.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		
		permissionsHandler = new PermissionsHandler(this);
		
		CommandHandler commandHandler = new CommandHandler(this);
		
		commandHandler.registerCommand(new DefineCommand(this));
		
		commandHandler.registerCommand(new ListCommand(this));
		
		commandHandler.registerCommand(new CreateGameCommand(this));
		
		getCommand("spleef").setExecutor(commandHandler);
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				load();
			}
		});
		
		log(getDescription().getVersion() + " enabled!");
	}
	
	public void log(String message){
		log.info("[nSpleef] " + message);
	}
	
	public Set<nSpleefArena> getArenas(){
		return arenas;
	}
	
	public nSpleefArena getArena(String name){
		for (nSpleefArena arena : arenas){
			if (arena.getName().equalsIgnoreCase(name)){
				return arena;
			}
		}
		
		return null;
	}
	
	public Set<Session> getSessions(){
		return sessions;
	}
	
	public Session getSession(Player player){
		for (Session session : sessions){
			if (session.getPlayer().equals(player)){
				return session;
			}
		}
		
		return createNewSession(player);
	}
	
	public boolean sessionExists(Player player){
		for (Session session : sessions){
			if (session.getPlayer().equals(player)){
				return true;
			}
		}
		
		return false;
	}
	
	public PermissionsHandler getPermissionsHandler(){
		return permissionsHandler;
	}
	
	public ConfigHandler getConfigHandler(){
		return configHandler;
	}
	
	private Session createNewSession(Player player){
		Session session = new Session(player);
		
		sessions.add(session);
		
		return session;
	}
	
	public void load(){
		loadConfig();
		
		loadArenas();
	}
	
	public void loadConfig(){
		File configFile = new File(getDataFolder(), "config.yml");
		
		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Configuration config = new Configuration(configFile);
		
		configHandler = new ConfigHandler(config);
	}
	
	public void loadArenas(){
		arenas.clear();
		
		getDataFolder().mkdir();
		
		File arenasFolder = new File(getDataFolder(), "arenas");
		
		arenasFolder.mkdir();
		
		for (String fileName : arenasFolder.list()){
			if (!fileName.endsWith(".yml")) continue;
			
			Configuration config = new Configuration(new File(arenasFolder, fileName));
			
			config.load();
			
			String name = config.getString("name");
			
			String worldName = config.getString("world");
			
			World world = getServer().getWorld(worldName);
			
			if (world == null) continue;
			
			Location block1 = new Location(world,
					config.getInt("block1.x", 0),
					config.getInt("block1.y", 0),
					config.getInt("block1.z", 0));
			
			Location block2 = new Location(world,
					config.getInt("block2.x", 0),
					config.getInt("block2.y", 0),
					config.getInt("block2.z", 0));
			
			arenas.add(new nSpleefArena(name, world, block1, block2));
		}
	}
}
