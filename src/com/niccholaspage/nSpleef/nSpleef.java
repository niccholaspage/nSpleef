package com.niccholaspage.nSpleef;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.niccholaspage.nSpleef.arena.nSpleefArena;
import com.niccholaspage.nSpleef.command.CommandHandler;
import com.niccholaspage.nSpleef.command.commands.*;
import com.niccholaspage.nSpleef.listeners.nSpleefPlayerListener;
import com.niccholaspage.nSpleef.player.Session;

public class nSpleef extends JavaPlugin {
	private Logger log;
	
	private Set<Session> sessions;
	
	private Set<nSpleefArena> arenas;
	
	private Set<nSpleefGame> games;
	
	private ConfigHandler configHandler;
	
	private PermissionsHandler permissionsHandler;
	
	private CommandHandler commandHandler;
	
	private File arenasFolder;

	@Override
	public void onDisable() {
		log("Disabled!");
	}

	@Override
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		
		arenas = new HashSet<nSpleefArena>();
		
		games = new HashSet<nSpleefGame>();
		
		sessions = new HashSet<Session>();
		
		nSpleefPlayerListener playerListener = new nSpleefPlayerListener(this);
		
		//Player snuff
		registerEvent(Type.PLAYER_INTERACT, playerListener);
		registerEvent(Type.PLAYER_COMMAND_PREPROCESS, playerListener);
		
		permissionsHandler = new PermissionsHandler(this);
		
		commandHandler = new CommandHandler(this);
		
		commandHandler.registerCommand(new DefineCommand(this));
		
		commandHandler.registerCommand(new ListCommand(this));
		
		commandHandler.registerCommand(new CreateGameCommand(this));
		
		commandHandler.registerCommand(new DeleteGameCommand(this));
		
		getCommand("spleef").setExecutor(commandHandler);
		
		arenasFolder = new File(getDataFolder(), "arenas");
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				load();
			}
		});
		
		log(getDescription().getVersion() + " enabled!");
	}
	
	private void registerEvent(Type type, Listener listener){
		registerEvent(type, listener, Priority.Normal);
	}
	
	private void registerEvent(Type type, Listener listener, Priority priority){
		getServer().getPluginManager().registerEvent(type, listener, priority, this);
	}
	
	public void log(String message){
		log.info("[nSpleef] " + message);
	}
	
	public Set<nSpleefArena> getArenas(){
		return arenas;
	}
	
	public Set<nSpleefGame> getGames(){
		return games;
	}
	
	public CommandHandler getCommandHandler(){
		return commandHandler;
	}
	
	public nSpleefArena getArena(String name){
		for (nSpleefArena arena : arenas){
			if (arena.getName().equalsIgnoreCase(name)){
				return arena;
			}
		}
		
		return null;
	}
	
	public nSpleefGame getGame(String name){
		for (nSpleefGame game : games){
			if (game.getName().equalsIgnoreCase(name)){
				return game;
			}
		}
		
		return null;
	}
	
	public nSpleefGame getGameByArena(nSpleefArena arena){
		for (nSpleefGame game : games){
			if (game.getArena().equals(arena)){
				return game;
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
		
		configHandler = new ConfigHandler(configFile);
	}
	
	public void loadArenas(){
		arenas.clear();
		
		getDataFolder().mkdir();
		
		arenasFolder.mkdir();
		
		for (String fileName : arenasFolder.list()){
			if (!fileName.endsWith(".yml")) continue;
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(arenasFolder, fileName));
			
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
			
			nSpleefArena arena = new nSpleefArena(name, world, block1, block2);
			
			//Lets read properties!
			for (DefaultProperty property : DefaultProperty.values()){
				String read = config.getString( "properties." + property);
				
				arena.getProperties().put(property + "", read);
			}
			
			arenas.add(arena);
		}
	}
	
	public File getArenasFolder(){
		return arenasFolder;
	}
}
