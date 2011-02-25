package com.niccholaspage.nSpleef;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * 
 * @author tommytony
 *
 */
//Tommytony's volume code.
//Thanks for letting me use it! ~niccholaspage
public class Volume {
	private final String name;
	private final World world;
	private Block cornerOne;
	private Block cornerTwo;
	private int[][][] blockTypes = null;
	private byte[][][] blockDatas = null;
	public Volume(String name, World world) {
		this.name = name;
		this.world = world;
	}
	public void setCornerOne(Block block) {
		this.cornerOne = block;
	}
	public void setCornerTwo(Block block) {
		this.cornerTwo = block;
	}
	public int saveBlocks() {
		int noOfSavedBlocks = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		try {
			if(hasTwoCorners()) {
				this.setBlockTypes(new int[getSizeX()][getSizeY()][getSizeZ()]);
				this.setBlockDatas(new byte[getSizeX()][getSizeY()][getSizeZ()]);
				x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							Block block = getWorld().getBlockAt(x, y, z);
							this.getBlockTypes()[i][j][k] = block.getTypeId();
							this.getBlockDatas()[i][j][k] = block.getData();
							z++;
							noOfSavedBlocks++;
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to save volume " + getName() + " blocks. Saved blocks:" + noOfSavedBlocks 
					+ ". Error at x:" + x + " y:" + y + " z:" + z + ". Exception:" + e.getClass().toString() + e.getMessage());
		}
		return noOfSavedBlocks;
	}
	public int resetBlocks() {
		int visitedBlocks = 0, noOfResetBlocks = 0, x = 0, y = 0, z = 0;
		int currentBlockId = 0;
		int oldBlockType = 0;
		clearBlocksThatDontFloat();
		try {
			if(hasTwoCorners() && getBlockTypes() != null) {
				x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							oldBlockType = getBlockTypes()[i][j][k];
							byte oldBlockData = getBlockDatas()[i][j][k];
							Block currentBlock = getWorld().getBlockAt(x, y, z);
							currentBlockId = currentBlock.getTypeId();
							if(currentBlockId != oldBlockType ||
								(currentBlockId == oldBlockType && currentBlock.getData() != oldBlockData ) ||
								(currentBlockId == oldBlockType && currentBlock.getData() == oldBlockData &&
										(oldBlockType == Material.WALL_SIGN.getId() || oldBlockType == Material.SIGN_POST.getId() 
												|| oldBlockType == Material.CHEST.getId() || oldBlockType == Material.DISPENSER.getId())
								)
							) {

//								if(oldBlockInfo.is(Material.SIGN) || oldBlockInfo.is(Material.SIGN_POST)) {
//									BlockState state = currentBlock.getState();
//									Sign currentSign = (Sign) state;
//									currentSign.setLine(0, oldBlockInfo.getSignLines()[0]);
//									currentSign.setLine(1, oldBlockInfo.getSignLines()[1]);
//									currentSign.setLine(2, oldBlockInfo.getSignLines()[2]);
//									currentSign.setLine(3, oldBlockInfo.getSignLines()[3]);
//									state.update();
//								}
									// regular block
									currentBlock.setType(Material.getMaterial(oldBlockType));
									currentBlock.setData(oldBlockData);
								noOfResetBlocks++;
							}
							visitedBlocks++;
							z++;
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to reset volume " + getName() + " blocks. Blocks visited: " + visitedBlocks 
					+ ". Blocks reset: "+ noOfResetBlocks + ". Error at x:" + x + " y:" + y + " z:" + z 
					+ ". Current block: " + currentBlockId + ". Old block: " + oldBlockType + ". Exception: " + e.getClass().toString() + " " + e.getMessage());
		}
		return noOfResetBlocks;
	}
	public byte[][][] getBlockDatas() {
		// TODO Auto-generated method stub
		return this.blockDatas;
	}
	
	public void setBlockDatas(byte[][][] data) {
		this.blockDatas = data;
	}
	
	
	public Block getMinXBlock() {
		if(cornerOne.getX() < cornerTwo.getX()) return cornerOne;
		return cornerTwo;
	}
	
	public Block getMinYBlock() {
		if(cornerOne.getY() < cornerTwo.getY()) return cornerOne;
		return cornerTwo;
	}
	
	public Block getMinZBlock() {
		if(cornerOne.getZ() < cornerTwo.getZ()) return cornerOne;
		return cornerTwo;
	}
	
	public int getMinX() {
		return getMinXBlock().getX();
	}
	
	public int getMinY() {
		return getMinYBlock().getY();
	}
	
	public int getMinZ() {
		return getMinZBlock().getZ();
	}
	
	public Block getMaxXBlock() {
		if(cornerOne.getX() < cornerTwo.getX()) return cornerTwo;
		return cornerOne;
	}
	
	public Block getMaxYBlock() {
		if(cornerOne.getY() < cornerTwo.getY()) return cornerTwo;
		return cornerOne;
	}
	
	public Block getMaxZBlock() {
		if(cornerOne.getZ() < cornerTwo.getZ()) return cornerTwo;
		return cornerOne;
	}
	
	public int getMaxX() {
		return getMaxXBlock().getX();
	}
	
	public int getMaxY() {
		return getMaxYBlock().getY();
	}
	
	public int getMaxZ() {
		return getMaxZBlock().getZ();
	}
	
	public int getSizeX() {
		return getMaxX() - getMinX() + 1;
	}
	
	public int getSizeY() {
		return getMaxY() - getMinY() + 1;
	}
	
	public int getSizeZ() {
		return getMaxZ() - getMinZ() + 1;
	}	

	public boolean isSaved() {
		return getBlockTypes() != null;
	}

	public int[][][] getBlockTypes() {
		return blockTypes;
	}
	
	public Block getCornerOne() {
		return cornerOne;
	}
	
	public Block getCornerTwo() {
		return cornerTwo;
	}

	public boolean contains(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		return hasTwoCorners() && x <= getMaxX() && x >= getMinX() && 
				y <= getMaxY() && y >= getMinY() &&
				z <= getMaxZ() && z >= getMinZ();
	}

	public boolean contains(Block block) {
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		return hasTwoCorners() && x <= getMaxX() && x >= getMinX() && 
				y <= getMaxY() && y >= getMinY() &&
				z <= getMaxZ() && z >= getMinZ();
	}

	public void setBlockTypes(int[][][] blockTypes) {
		this.blockTypes = blockTypes;
	}

	public String getName() {
		return name;
	}
	public void setToMaterial(Material material) {
		try {
			if(hasTwoCorners() && getBlockTypes() != null) {
				int x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					int y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						int z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							Block currentBlock = getWorld().getBlockAt(x, y, z);
							currentBlock.setType(material);
							z++;
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to set block to " + material + "in volume " + name + "." + e.getClass().toString() + " " + e.getMessage());
		}
	}
	
	public void setFaceMaterial(BlockFace face, Material material) {
		try {
			if(hasTwoCorners() && getBlockTypes() != null) {
				int x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					int y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						int z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							if((face == BlockFace.DOWN && y == getMinY()) 
									|| (face == BlockFace.UP && y == getMaxY())
									|| (face == BlockFace.NORTH && x == getMinX())
									|| (face == BlockFace.EAST && z == getMinZ())
									|| (face == BlockFace.SOUTH && x == getMaxX())
									|| (face == BlockFace.WEST && z == getMaxZ())) {
								Block currentBlock = getWorld().getBlockAt(x, y, z);
								currentBlock.setType(material);
							}
							z++;
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to set block to " + material + "in volume " + name + "." + e.getClass().toString() + " " + e.getMessage());
		}
	}
	
	private void switchMaterials(Material[] oldTypes, Material newType) {
		try {
			if(hasTwoCorners() && getBlockTypes() != null) {
				int x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					int y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						int z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							Block currentBlock = getWorld().getBlockAt(x, y, z);
							for(Material oldType : oldTypes) {
								if(currentBlock.getType().getId() == oldType.getId()) {
									currentBlock.setType(newType);
//									BlockState state = currentBlock.getState();
//									if(state != null) {
//										state.setType(newType);
//										state.update(true);
//									}
								}
							}
							z++;
						}
						y++;
					}
					x++;
				}
			}	
		} catch (Exception e) {
			System.out.println("Failed to switch block to " + newType + "in volume " + name + "." + e.getClass().toString() + " " + e.getMessage());
		}
	}
	public boolean hasTwoCorners() {
		return cornerOne != null && cornerTwo != null;
	}
	public World getWorld() {
		return world;
	}
	public void clearBlocksThatDontFloat() {
		Material[] toAirMaterials = new Material[22];
		toAirMaterials[0] = Material.SIGN_POST;
		toAirMaterials[1] = Material.WALL_SIGN;
		//toAirMaterials[2] = Material.IRON_DOOR_BLOCK;
		//toAirMaterials[3] = Material.WOODEN_DOOR;
		toAirMaterials[2] = Material.IRON_DOOR;
		toAirMaterials[3] = Material.WOOD_DOOR;
		toAirMaterials[4] = Material.LADDER;
		toAirMaterials[5] = Material.YELLOW_FLOWER;
		toAirMaterials[6] = Material.RED_ROSE;
		toAirMaterials[7] = Material.RED_MUSHROOM;
		toAirMaterials[8] = Material.BROWN_MUSHROOM;
		toAirMaterials[9] = Material.SAPLING;
		toAirMaterials[10] = Material.TORCH;
		toAirMaterials[11] = Material.RAILS;
		toAirMaterials[12] = Material.STONE_BUTTON;
		toAirMaterials[13] = Material.STONE_PLATE;
		toAirMaterials[14] = Material.WOOD_PLATE;
		toAirMaterials[15] = Material.LEVER;
		toAirMaterials[16] = Material.REDSTONE;
		toAirMaterials[17] = Material.REDSTONE_TORCH_ON;
		toAirMaterials[18] = Material.REDSTONE_TORCH_OFF;
		toAirMaterials[19] = Material.CACTUS;
		toAirMaterials[20] = Material.SNOW;
		toAirMaterials[21] = Material.ICE;
		switchMaterials(toAirMaterials, Material.AIR);
	}


}