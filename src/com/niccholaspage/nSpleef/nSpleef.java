//The Package
package com.niccholaspage.nSpleef;
//All the imports
import com.niccholaspage.nSpleef.commands.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.block.Block;
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
	//Links the nSpleefPlayerListener
	private final nSpleefPlayerListener playerListener = new nSpleefPlayerListener(this);
	//Links the nSpleefBlockListener
    private final nSpleefBlockListener blockListener = new nSpleefBlockListener(this);
    //Entity Listener
    private final nSpleefEntityListener entityListener = new nSpleefEntityListener(this);
    
    //Create the hashmap "nSpleefUsers"
    public final HashMap<Player, ArrayList<Block>> nSpleefUsers = new HashMap<Player, ArrayList<Block>>();
    //Create the games array
    public final ArrayList<String> nSpleefGames = new ArrayList<String>();
    //Persistent games
    public Boolean persistentgames;
    //Give money on leave
    public boolean giveMoneyOnLeave;
    //Give money on disconnect
    public boolean giveMoneyOnDisconnect;
    //Can the player place blocks during spleef?
    public boolean canplaceblocks;
    //Create arena array
    public ArrayList<nSpleefArena> nSpleefArenas = new ArrayList<nSpleefArena>();
    
	@Override
	//When the plugin is disabled this method is called.
	public void onDisable() {
		for (int i = 0; i < nSpleefArenas.size(); i++){
			if (nSpleefArenas.get(i).getPlayersIn().size() > 0){
				System.out.println(nSpleefMessage("Restoring arena " + nSpleefArenas.get(i).getName()));
				for (int j = 0; j < nSpleefArenas.get(i).getPlayersIn().size(); j++){
					nSpleefArenas.get(i).getPlayersIn().get(j).teleport(nSpleefArenas.get(i).getPlayersLocation().get(j));
				}
				nSpleefArenas.get(i).getVolume().resetBlocks();
			}
		}
		if (new File("plugins/nSpleef/games.txt").exists()) new File("plugins/nSpleef/games.txt").delete();
		if (persistentgames){
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
		ArrayList<String> data = Util.filetoarray(in);
		for (int i = 0; i < data.size(); i++){
			nSpleefGames.add(data.get(i));
		}
	}
	
    private void readConfig() {
		File file = new File("plugins/nSpleef/");
		if (!(file.exists())){
			file.mkdir();
		}
    	Configuration _config = new Configuration(new File("plugins/nSpleef/config.yml"));

    	_config.load();
		file = new File("plugins/nSpleef/config.yml");
    	if (!file.exists()){
    		Util.createDefaultConfig();
    	}
    	// Reading from yml file
    	canplaceblocks = _config.getBoolean("nSpleef.canplaceblocks", false);
    	persistentgames = _config.getBoolean("nSpleef.persistentgames", false);
    	giveMoneyOnLeave = _config.getBoolean("nSpleef.givemoneyonleave", false);
    	giveMoneyOnDisconnect = _config.getBoolean("nSpleef.givemoneyondisconnect", false);
        }
    private void registerCommands(){
    	CommandHandler commandHandler = new CommandHandler(this);
    	getCommand("spleef").setExecutor(commandHandler);
    	commandHandler.registerExecutor("define", new DefineCommand(this), "/spleef define arena");
    	commandHandler.registerExecutor("join", new JoinCommand(this), "/spleef join game");
    	commandHandler.registerExecutor("leave", new LeaveCommand(this), "/spleef leave");
    	commandHandler.registerExecutor("list", new ListCommand(this), "/spleef list");
    	commandHandler.registerExecutor("deletegame", new DeleteGameCommand(this), "/spleef deletegame name");
    	commandHandler.registerExecutor("creategame", new CreateGameCommand(this), "/spleef creategame name arena <money>");
    	commandHandler.registerExecutor("deletearena", new DeleteArenaCommand(this), "/spleef deletearena arena");
    }
    public String nSpleefMessage(String message){
    	return "[nSpleef] " + message;
    }
    public boolean isInt(String i){
    	try {
    		Integer.parseInt(i);
    		return true;
    	} catch(NumberFormatException nfe){
    		return false;
    	}
    }
	@Override
	//When the plugin is enabled this method is called.
	public void onEnable() {
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
		//PlayerListener stuff
	    pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
	    //BlockListener stuff
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        //EntityListener stuff
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Normal, this);
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Init Data
        Data.init(this);
        //Init Filter
        Filter.init(this);
        //Setup arenas
	    Data.setupArrays();
	    //Setup config
	    readConfig();
	    //Read Games
	    if (persistentgames) readGames();
        //Setup Permissions
        PermissionHandler.init(getServer());
        //Setup economy
        EconomyHandler.init(getServer());
	    //Commands
	    registerCommands();
        //Print that the plugin has been enabled!
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		
	}
	public void leave(Player player, Integer mode){
		//Mode 0: Disconnect
		//Mode 1: Leave
		//Mode 2: Deleted arena or game
		if (mode == 1) if (!(PermissionHandler.has(player, "nSpleef.member.leave"))) return;
	    if (nSpleefArenas.size() == 0) return;
		 for (int i = 0; i <= nSpleefArenas.size() - 1; i++){
			 for (int j = 0; j <= nSpleefArenas.get(i).getPlayersIn().size() - 1; j++){
				 if (player.equals(nSpleefArenas.get(i).getPlayersIn().get(j))){
					 	nSpleefArenas.get(i).getPlayerStatus().remove(j);
					 	nSpleefArenas.get(i).getPlayersIn().remove(j);
					 	nSpleefArenas.get(i).getPlayers().remove(player);
					 	player.teleport(nSpleefArenas.get(i).getPlayersLocation().get(j));
					 	nSpleefArenas.get(i).getPlayersLocation().remove(j);
						if (nSpleefArenas.get(i).getGame().split(",").length > 3){
							if ((mode == 2) && (giveMoneyOnLeave)) EconomyHandler.addMoney(player, Integer.parseInt(nSpleefArenas.get(i).getGame().split(",")[3]));
							if ((mode == 1) && (giveMoneyOnLeave)) EconomyHandler.addMoney(player, Integer.parseInt(nSpleefArenas.get(i).getGame().split(",")[3]));
							if ((mode == 0) && (giveMoneyOnDisconnect)) EconomyHandler.addMoney(player, Integer.parseInt(nSpleefArenas.get(i).getGame().split(",")[3]));
						}
						nSpleefArenas.get(i).leave(player);
				 }
			 }
		 }
	}
}
