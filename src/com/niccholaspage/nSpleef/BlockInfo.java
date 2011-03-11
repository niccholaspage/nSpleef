package com.niccholaspage.nSpleef;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * 
 * @author tommytony
 *
 */
public class BlockInfo {
	private int x;
	private int y;
	private int z;
	private int type;
	private byte data;
	//private String[] signLines;

	public static Block getBlock(World world, BlockInfo info) {
		return world.getBlockAt(info.getX(), info.getY(), info.getZ());
	}
	
	public BlockInfo(Block block) {
		this.x = block.getX();
		this.y = block.getY();
		this.z = block.getZ();
		this.type = block.getTypeId();
		this.data = block.getData();
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getTypeId() {
		return type;
	}
	
	public Material getType() {
		return Material.getMaterial(type);
	}	
	
	public byte getData() {
		return data;
	}
	
	public boolean is(Material material) {
		return getType() == material;
	}
}