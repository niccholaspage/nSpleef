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
//		if(is(Material.SIGN) || is(Material.SIGN_POST)) {
//			Sign sign = (Sign)block.getState();
//			this.signLines = sign.getLines();
//		}
	}
	
//	public BlockInfo(BlockState blockState) {
//		this.x = blockState.getX();
//		this.y = blockState.getY();
//		this.z = blockState.getZ();
//		this.type = blockState.getTypeId();
//		this.data = blockState.getData().getData();
////		if(is(Material.SIGN) || is(Material.SIGN_POST)) {
////			Sign sign = (Sign)blockState;
////			this.signLines = sign.getLines();
////		}
//	}
	
//	public BlockInfo(int typeID, byte data, String[] lines) {
//		type = typeID;
//		this.data = data;
//		//signLines = lines;
//	}

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
	
//	public String[] getSignLines() {
//		if(is(Material.SIGN) || is(Material.SIGN_POST)){
//			return new String[4] {"", ""};
//		}
//		return null;
//	}
}