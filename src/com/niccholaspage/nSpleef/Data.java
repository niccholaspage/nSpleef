package com.niccholaspage.nSpleef;

import java.util.ArrayList;
import java.util.List;

public class Data {
	public static nSpleef plugin;
	  public static void init (nSpleef instance){
		  plugin = instance;
	  }
	  public static void setupArenas(){
		    List<String> data = new ArrayList<String>();
		    plugin.nSpleefArenas = new ArrayList<nSpleefArena>();
		    if (Util.exists("arenas.txt") == false) return;
		    Util.openfileread("arenas.txt");
		    data = Util.filetoarray();
		    Util.closefileread();
		    for (int i = 0; i < data.size(); i++) {
		    	String[] split = data.get(i).split(":");
		    	addArena(split);
		    }
	  }
	  
	  public static void addArena(String[] split){
	    	nSpleefArena a = new nSpleefArena(split[0],plugin.getServer().getWorld(split[7]), plugin);
	    	a.setFirstBlock(Util.toVectorxyz(Integer.parseInt((split[1])), Integer.parseInt((split[2])), Integer.parseInt((split[3]))));
	    	a.setSecondBlock(Util.toVectorxyz(Integer.parseInt((split[4])), Integer.parseInt((split[5])), Integer.parseInt((split[6]))));
	    	a.setTpBlock();
			a.createVolume();
			a.getVolume().setCornerOne(a.getFirstBlock().toLocation(a.getWorld()).getBlock());
			a.getVolume().setCornerTwo(a.getSecondBlock().toLocation(a.getWorld()).getBlock());
			a.getVolume().saveBlocks();
	    	plugin.nSpleefArenas.add(a);
	  }
}