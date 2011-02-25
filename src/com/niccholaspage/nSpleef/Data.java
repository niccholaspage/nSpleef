package com.niccholaspage.nSpleef;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.util.BlockVector;

public class Data {
	public ArrayList<String> data;
	public static ArrayList<String> titledata;
	public static BlockVector[] block1;
	public BlockVector[] block2;
	public static World[] worlds;
	public static nSpleef plugin;
	  public Data(nSpleef instance) {
	        plugin = instance;
	    }
	  public static ArrayList<String> returntitledata(){
		  return titledata;
	  }
	  public static BlockVector[] returnblock1(){
		  return block1;
	  }
	  public static World[] returnworlds(){
		  return worlds;
	  }
	  public static void setupArrays(){
		  if (Util.exists() == false){
			  return;
		  }
		    ArrayList<String> data = new ArrayList<String>();
		    ArrayList<String> titledata = new ArrayList<String>();
		    Util.openfileread();
		    data = Util.filetoarray();
		    BlockVector[] block1 = new BlockVector[data.size()];
		    BlockVector[] block2 = new BlockVector[data.size()];
		    World[] worlds = new World[data.size()];
		    nSpleefArena a;
		    for (int i = 0; i <= data.size() - 1; i++) {
		    	titledata.add(data.get(i).split(":")[0]);
		    	block1[i] = Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[1])), Integer.parseInt((data.get(i).split(":")[2])), Integer.parseInt((data.get(i).split(":")[3])));
		    	block2[i] = Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[4])), Integer.parseInt((data.get(i).split(":")[5])), Integer.parseInt((data.get(i).split(":")[6])));
		    	worlds[i] = plugin.getServer().getWorld(data.get(i).split(":")[7]);
		    	a = new nSpleefArena(data.get(i).split(":")[0],plugin.getServer().getWorld(data.get(i).split(":")[7]));
		    	a.setFirstBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[1])), Integer.parseInt((data.get(i).split(":")[2])), Integer.parseInt((data.get(i).split(":")[3]))));
		    	a.setSecondBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[4])), Integer.parseInt((data.get(i).split(":")[5])), Integer.parseInt((data.get(i).split(":")[6]))));
		    	plugin.nSpleefArenas.add(a);
		    }
		    Util.closefileread();
	  }
}
