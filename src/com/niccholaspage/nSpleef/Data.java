package com.niccholaspage.nSpleef;

import java.util.ArrayList;

public class Data {
	public ArrayList<String> data;
	public static nSpleef plugin;
	  public static void init (nSpleef instance){
		  plugin = instance;
	  }
	  public static void setupArrays(){
		    ArrayList<String> data = new ArrayList<String>();
		    plugin.nSpleefArenas = new ArrayList<nSpleefArena>();
		    if (Util.exists("arenas.txt") == false) return;
		    Util.openfileread("arenas.txt");
		    data = Util.filetoarray();
		    Util.closefileread();
		    for (int i = 0; i < data.size(); i++) {
		    	nSpleefArena a = new nSpleefArena(data.get(i).split(":")[0],plugin.getServer().getWorld(data.get(i).split(":")[7]), plugin);
		    	a.setFirstBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[1])), Integer.parseInt((data.get(i).split(":")[2])), Integer.parseInt((data.get(i).split(":")[3]))));
		    	a.setSecondBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[4])), Integer.parseInt((data.get(i).split(":")[5])), Integer.parseInt((data.get(i).split(":")[6]))));
		    	a.setTpBlock();
				a.createVolume();
				a.getVolume().setCornerOne(a.getFirstBlock().toLocation(a.getWorld()).getBlock());
				a.getVolume().setCornerTwo(a.getSecondBlock().toLocation(a.getWorld()).getBlock());
				a.getVolume().saveBlocks();
		    	plugin.nSpleefArenas.add(a);
		    }
	  }
}