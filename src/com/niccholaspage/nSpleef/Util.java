package com.niccholaspage.nSpleef;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class Util {
	  static BufferedWriter out;
	  static BufferedReader in;
	  public static void waitMS(long s){
			try {
				Thread.sleep(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	  }
	  public static Boolean returnBlockInArea(BlockVector block, BlockVector b1, BlockVector b2){
		  if ((block.getBlockX() >= b1.getBlockX()) && (block.getBlockX() <= b2.getBlockX()) || (block.getBlockX() >= b2.getBlockX()) && (block.getBlockX() <= b1.getBlockX())){
			  if ((block.getBlockY() >= b1.getBlockY()) && (block.getBlockY() <= b2.getBlockY()) || (block.getBlockY() >= b2.getBlockY()) && (block.getBlockY() <= b1.getBlockY())){
				  if ((block.getBlockZ() >= b1.getBlockZ()) && (block.getBlockZ() <= b2.getBlockZ()) || (block.getBlockZ() >= b2.getBlockZ()) && (block.getBlockZ() <= b1.getBlockZ())){
					  return true;
				  }
			  }
		  }
	return false;
	}
	  public static BlockVector toVector(Block block){
		    return new BlockVector(block.getX(), block.getY(), block.getZ());
	  }
	  public static BlockVector toBlockVector(Vector block){
		  return new BlockVector(block.getBlockX(), block.getBlockY(), block.getBlockZ());
	  }
	  public static BlockVector toVectorxyz(int x, int y, int z)
	  {
		    return new BlockVector(x, y, z);
	  }
	  public static Boolean exists(String name){
			if (new File("plugins/nSpleef/" + name).exists()) return true; else return false;
	  }
	  public static void openfileread(String name){
			File f = new File("plugins/nSpleef/");
			if (!(f.exists())){
				f.mkdir();
			}
	      try{
	    	    FileReader fstream = new FileReader("plugins/nSpleef/" + name);
	    	    in = new BufferedReader(fstream);
	    	    }catch (Exception e){//Catch exception if any
	    	      System.out.println("");
	    	    }
	  }
		public static void closefileread(){
			try {
		    in.close();
			} catch (IOException e)
			{
			}
		}
		public static void openfile(String name){
			File f = new File("plugins/nSpleef/");
			if (!(f.exists())){
				f.mkdir();
			}
		      try{
		    	    // Create file 
		    	    FileWriter fstream = new FileWriter("plugins/nSpleef/" + name,true);
		    	    out = new BufferedWriter(fstream);
		    	    }catch (Exception e){//Catch exception if any
		    	    }
		}
		public static int countlines(String filename) throws IOException {
		    InputStream is = new BufferedInputStream(new FileInputStream(filename));
		    byte[] c = new byte[1024];
		    int count = 0;
		    int readChars = 0;
		    while ((readChars = is.read(c)) != -1) {
		        for (int i = 0; i < readChars; ++i) {
		            if (c[i] == '\n')
		                ++count;
		        }
		    }
		    return count;
		}
		public static ArrayList<String> filetoarray(){
			  String line = "";
			  ArrayList<String> data = new ArrayList<String>();
			  try {
			   while((line = in.readLine()) != null) {
			 
			    data.add(line);
			   }
			  }
			  catch(FileNotFoundException fN) {
			  }
			  catch(IOException e) {
			  }
			  return data;
		}
		public static ArrayList<String> filetoarray(BufferedReader in){
			  String line = "";
			  ArrayList<String> data = new ArrayList<String>();
			  try {
			   while((line = in.readLine()) != null) {
			 
			    data.add(line);
			   }
			  }
			  catch(FileNotFoundException fN) {
			  }
			  catch(IOException e) {
			  }
			  return data;
		}
		public static void replaceFile(ArrayList<String> s){
			try {
			for (int i = 0; i<= s.size() - 1; i++){
				out.write(s.get(i));
			}
			}catch (Exception e){
				
			}
		}
		public static void writefile(String j){
			try{
    	    out.write(j);
			}catch (Exception e){
			}
		}
		public static String readline(){
			try{
	    	    return in.readLine();
				}catch (Exception e){
					return null;
				}
		}
		public static void closefile(){
			try {
		    out.close();
			} catch (IOException e)
			{
			}
		}
		public static void createDefaultConfig(){
  	      try{
	    	    BufferedWriter out = new BufferedWriter(new FileWriter("plugins/nSpleef/config.yml"));
	    	    out.write("nSpleef:\n");
	    	    out.write("    canplaceblocks: false\n");
	    	    out.write("    persistentgames: false\n");
	    	    out.write("    givemoneyonleave: false\n");
	    	    out.write("    givemoneyondisconnect: false\n");
	    	    out.write("    givemoneyonkick: false\n");
	    	    out.write("    joinkickertime: 0\n");
	    	    out.close();
  	      }catch (Exception e){//Catch exception if any
  	    	  System.out.println("[nSpleef] Could not write the default config file.");
  	      }
		}
}
