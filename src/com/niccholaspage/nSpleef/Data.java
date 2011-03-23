package com.niccholaspage.nSpleef;

import java.util.ArrayList;

public class Data {
	public ArrayList<String> data;
	//public static ArrayList<String> titledata;
	//public static BlockVector[] block1;
	//public BlockVector[] block2;
	//public static World[] worlds;
	public static nSpleef plugin;
	  public Data(nSpleef instance) {
	        plugin = instance;
	    }
	  public static void setupArrays(){
		  if (Util.exists() == false){
			  return;
		  }
		    ArrayList<String> data = new ArrayList<String>();
		    plugin.nSpleefArenas = new ArrayList<nSpleefArena>();
		    Util.openfileread();
		    data = Util.filetoarray();
		    for (int i = 0; i < data.size() - 1; i++) {
		    	nSpleefArena a = new nSpleefArena(data.get(i).split(":")[0],plugin.getServer().getWorld(data.get(i).split(":")[7]));
		    	a.setFirstBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[1])), Integer.parseInt((data.get(i).split(":")[2])), Integer.parseInt((data.get(i).split(":")[3]))));
		    	a.setSecondBlock(Util.toVectorxyz(Integer.parseInt((data.get(i).split(":")[4])), Integer.parseInt((data.get(i).split(":")[5])), Integer.parseInt((data.get(i).split(":")[6]))));
		    	a.setTpBlock();
		    	plugin.nSpleefArenas.add(a);
		    }
		    Util.closefileread();
	  }
}