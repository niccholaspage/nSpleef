//The Package
package com.niccholaspage.nSpleef;
//All the imports
import com.niccholaspage.nSpleef.commands.*;
import com.niccholaspage.nSpleef.listeners.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

/**
 * nSpleef for Bukkit
 *
 * @author niccholaspage
 * 
 */
//Starts the class
public class nSpleef extends JavaPlugin{
	//Player Listener
	private final nSpleefPlayerListener playerListener = new nSpleefPlayerListener(this);
	//Block Listener
    private final nSpleefBlockListener blockListener = new nSpleefBlockListener(this);
    //Entity Listener
    private final nSpleefEntityListener entityListener = new nSpleefEntityListener(this);
    //Command Handler is now public for the help command
    public final CommandHandler commandHandler = new CommandHandler(this);
    //Is instant mining enabled?
    public boolean instantMine;
    //Persistent games
    public boolean persistentGames;
    //Give money on leave
    public boolean giveMoneyOnLeave;
    //Give money on disconnect
    public boolean giveMoneyOnDisconnect;
    //Give money on kick
    public boolean giveMoneyOnKick;
    //Can the player place blocks during spleef?
    public boolean canPlaceBlocks;
    //How long until the player gets booted after joining?
    public int joinKickerTime;
    //Create arena array
    public final List<nSpleefArena> nSpleefArenas = new ArrayList<nSpleefArena>();
    //Create the games array
    public final List<nSpleefGame> nSpleefGames = new ArrayList<nSpleefGame>();
    
	public void onDisable() {
		for (int i = 0; i < nSpleefArenas.size(); i++){
			if (nSpleefArenas.get(i).getPlayersIn().size() > 0){
				System.out.println("[nSpleef] Restoring arena " + nSpleefArenas.get(i).getName());
				for (int j = 0; j < nSpleefArenas.get(i).getPlayersIn().size(); j++){
					nSpleefArenas.get(i).getPlayersIn().get(j).teleport(nSpleefArenas.get(i).getPlayersLocation().get(j));
				}
				nSpleefArenas.get(i).getVolume().resetBlocks();
			}
		}
		if (new File("plugins/nSpleef/games.txt").exists()) new File("plugins/nSpleef/games.txt").delete();
		if (persistentGames){
			if (nSpleefGames.size() > 0){
				File file = new File("plugins/nSpleef/games.txt");
				BufferedWriter out = null;
				try {
					file.createNewFile();
					out = new BufferedWriter(new FileWriter("plugins/nSpleef/games.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < nSpleefGames.size(); i++){
					try {
						out.write(nSpleefGames.get(i) + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
		getServer().getScheduler().cancelTasks(this);
		System.out.println("nSpleef Disabled");
	}
	
	private void readGames(){
		File file = new File("plugins/nSpleef/games.txt");
		if (!(file.exists())) return;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("plugins/nSpleef/games.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> data = Util.filetoarray(in);
		for (int i = 0; i < data.size(); i++){
			String[] split;
			split = data.get(i).split(",");
			nSpleefGame game = new nSpleefGame(split[0], split[1], split[2]);
			nSpleefGames.add(game);
			if (split.length > 3) game.setMoney(Integer.parseInt(split[3]));
			if (split.length > 4) game.setMode(Integer.parseInt(split[4]));
		}
	}

    private boolean createFile(String file){
		File f = new File(file);
		if (!f.exists()){
			if (file.endsWith("/")){
				f.mkdir();
			}else {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}else {
			return false;
		}
    }
    
	private void writeNode(String node,Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
	
	private void writeOptions(Configuration config){
		writeNode("nSpleef", "", config);
		writeNode("nSpleef.instantmine", true, config);
		writeNode("nSpleef.canplaceblocks", false, config);
		writeNode("nSpleef.persistentgames", true, config);
		writeNode("nSpleef.givemoneyonleave", false, config);
		writeNode("nSpleef.givemoneyondisconnect", false, config);
		writeNode("nSpleef.givemoneyonkick", false, config);
		writeNode("nSpleef.joinkickertime", false, config);
	}
    
    private void readConfig() {
		createFile("plugins/nSpleef/");
		createFile("plugins/nSpleef/config.yml");
    	Configuration config = new Configuration(new File("plugins/nSpleef/config.yml"));
    	config.load();
    	writeOptions(config);
    	config.save();
    	// Reading from yml file
    	instantMine = config.getBoolean("nSpleef.instantmine", true);
    	canPlaceBlocks = config.getBoolean("nSpleef.canplaceblocks", false);
    	persistentGames = config.getBoolean("nSpleef.persistentgames", false);
    	giveMoneyOnLeave = config.getBoolean("nSpleef.givemoneyonleave", false);
    	giveMoneyOnDisconnect = config.getBoolean("nSpleef.givemoneyondisconnect", false);
    	giveMoneyOnKick = config.getBoolean("nSpleef.givemoneyonkick", false);
    	joinKickerTime = config.getInt("nSpleef.joinkickertime", 0);
        }
    
    private void registerEvents(){
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
		//PlayerListener stuff
	    pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
	    //BlockListener stuff
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        //EntityListener stuff
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
    }
    
    private void registerCommands(){
    	getCommand("ready").setExecutor(commandHandler);
    	getCommand("spleef").setExecutor(commandHandler);
    	commandHandler.registerExecutor("define", new DefineCommand(this), "/spleef define arena", "nSpleef.admin.define");
    	commandHandler.registerExecutor("join", new JoinCommand(this), "/spleef join game", "nSpleef.member.join");
    	commandHandler.registerExecutor("leave", new LeaveCommand(this), "/spleef leave", "nSpleef.member.leave");
    	commandHandler.registerExecutor("list", new ListCommand(this), "/spleef list", "nSpleef.member.list");
    	commandHandler.registerExecutor("deletegame", new DeleteGameCommand(this), "/spleef deletegame name", "nSpleef.member.deletegame");
    	commandHandler.registerExecutor("creategame", new CreateGameCommand(this), "/spleef creategame name arena <money>", "nSpleef.member.creategame");
    	commandHandler.registerExecutor("deletearena", new DeleteArenaCommand(this), "/spleef deletearena arena", "nSpleef.admin.deletearena");
    	commandHandler.registerExecutor("ready", new ReadyCommand(this), "/spleef ready", "");
    	commandHandler.registerExecutor("forceready", new ForceReadyCommand(this), "/spleef forceready", "nSpleef.admin.forceready");
    	commandHandler.registerExecutor("help", new HelpCommand(this), "/spleef help", "");
    	commandHandler.registerExecutor("?", new HelpCommand(this), "/spleef ?", "");
    	commandHandler.registerExecutor("forceleave", new ForceLeaveCommand(this), "/spleef forceleave player", "nSpleef.admin.forceleave");
    }
    
    public boolean isInt(String i){
    	try {
    		Integer.parseInt(i);
    		return true;
    	} catch(NumberFormatException nfe){
    		return false;
    	}
    }

	public void onEnable() {
		registerEvents();
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Init Data
        Data.init(this);
        //Init Filter
        Filter.init(this);
        //Setup arenas
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
        	public void run(){
        		Data.setupArenas();
        		System.out.println("[nSpleef] Arenas loaded!");
        	}
        });
	    //Setup config
	    readConfig();
	    //Read Games
	    if (persistentGames) readGames();
        //Setup Permissions
        PermissionHandler.init(getServer());
        //Setup economy
        EconomyHandler.init(getServer());
	    //Commands
	    registerCommands();
        //Print that the plugin has been enabled!
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		
	}
	public void ready(Player player){
		 nSpleefArena arena = Filter.getArenaByPlayerIn(player);
		 if (arena == null) return;
		 if (arena.getInGame() > 0) return;
		 arena.getPlayerStatus().set(arena.getPlayers().indexOf(player), true);
		 arena.checkReady();
	}
}
