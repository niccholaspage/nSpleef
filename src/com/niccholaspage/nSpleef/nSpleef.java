//The Package
package com.niccholaspage.nSpleef;
//All the imports
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
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
    //Links nSpleefMonsterListener
    //private final nSpleefMonsterListener monsterListener = new nSpleefMonsterListener(this);
    //Links the Data class
    @SuppressWarnings("unused")
	private final Data data = new Data(this);
    //Create the hashmap "nSpleefUsers"
    public final HashMap<Player, ArrayList<Block>> nSpleefUsers = new HashMap<Player, ArrayList<Block>>();
    //Create the nSpleefPlayers array
    public ArrayList<nSpleefPlayer> nSpleefPlayers = new ArrayList<nSpleefPlayer>();
    //Create the games array
    public final ArrayList<String> nSpleefGames = new ArrayList<String>();
    //Create arena array
    public ArrayList<nSpleefArena> nSpleefArenas = new ArrayList<nSpleefArena>();

    public static PermissionHandler Permissions;
	//public nSpleef(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
    //{
    //    super(pluginLoader, instance, desc, folder, plugin, cLoader);
    //  }
	@Override
	//When the plugin is disabled this method is called.
	public void onDisable() {
		//Print "nSpleef Disabled" on the log.
		System.out.println("nSpleef Disabled");
		
	}
	
    public void readConfig() {
		File file = new File("plugins/nSpleef/");
		if (!(file.exists())){
			file.mkdir();
		}
    	Configuration _config = new Configuration(new File("plugins/nSpleef/config.yml"));

    	_config.load();
		file = new File("plugins/nSpleef/config.yml");
    	if (!file.exists()){
    	      try{
    	    	    // Create file 
    	    	    FileWriter fstream = new FileWriter("plugins/nSpleef/config.yml");
    	    	    BufferedWriter out = new BufferedWriter(fstream);
    	    	    out.write("nSpleef:\n");
    	    	    out.write("    canplaceblocks: false\n");
    	    	    //Close the output stream
    	    	    out.close();
    	    	    }catch (Exception e){//Catch exception if any
    	    	      System.out.println("nSpleef could not write the default config file.");
    	    	    }
    	}
    	// Reading from yml file
    	Boolean canplaceblocks = _config.getBoolean("nSpleef.canplaceblocks", false);
    	nSpleefBlockListener.setConfig(canplaceblocks);
        }
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (nSpleef.Permissions == null) {
            if (test != null) {
                nSpleef.Permissions = ((Permissions)test).getHandler();
            } else {
            	System.out.println("Permissions system not detected, disabling plugin.");
            	this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

	@Override
	//When the plugin is enabled this method is called.
	public void onEnable() {
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
		//PlayerListener stuff
	    pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
	    //BlockListener stuff
        pm.registerEvent(Event.Type.BLOCK_PLACED, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGED, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Event.Priority.Normal, this);
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Setup Permissions
        setupPermissions();
        //Setup data(Yay!)
	    Data.setupArrays();
	    //Setup config
	    readConfig();
        //Print that the plugin has been enabled!
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		
	}
}
