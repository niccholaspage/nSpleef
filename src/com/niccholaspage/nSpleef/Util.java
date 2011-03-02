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
	  public static BlockVector toVector(Block block)
	  {
		    return new BlockVector(block.getX(), block.getY(), block.getZ());
	  }
	  public static BlockVector toBlockVector(Vector block){
		  return new BlockVector(block.getBlockX(), block.getBlockY(), block.getBlockZ());
	  }
	  public static BlockVector toVectorxyz(int x, int y, int z)
	  {
		    return new BlockVector(x, y, z);
	  }
	  public static Boolean exists(){
			File f = new File("plugins/nSpleef/arenas.txt");
			if (f.exists()){
				return true;
			}else {
				return false;
			}
	  }
	  public static void openfileread(){
			File f = new File("plugins/nSpleef/");
			if (!(f.exists())){
				f.mkdir();
			}
	      try{
	    	    FileReader fstream = new FileReader("plugins/nSpleef/arenas.txt");
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
		public static void openfile(){
			File f = new File("plugins/nSpleef/");
			if (!(f.exists())){
				f.mkdir();
			}
		      try{
		    	    // Create file 
		    	    FileWriter fstream = new FileWriter("plugins/nSpleef/arenas.txt",true);
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
}
