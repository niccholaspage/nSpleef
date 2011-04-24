package com.niccholaspage.nSpleef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author tommytony
 *
 */
public class Volume {
	//Thanks for letting me use your code ~niccholaspage
	private final String name;
	private final World world;
	private BlockInfo cornerOne;
	private BlockInfo cornerTwo;
	private int[][][] blockTypes = null;
	private byte[][][] blockDatas = null;
	private HashMap<String, String[]> signLines = new HashMap<String, String[]>();
	private HashMap<String, List<ItemStack>> invBlockContents = new HashMap<String, List<ItemStack>>();

	public Volume(String name, World world) {
		this.name = name;
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean hasTwoCorners() {
		return cornerOne != null && cornerTwo != null;
	}
	
	public void setCornerOne(Block block) {
		this.cornerOne = new BlockInfo(block);
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
				this.getSignLines().clear();
				this.getInvBlockContents().clear();
				x = getMinX();
				for(int i = 0; i < getSizeX(); i++){
					y = getMinY();
					for(int j = 0; j < getSizeY(); j++){
						z = getMinZ();
						for(int k = 0;k < getSizeZ(); k++) {
							try {
								Block block = getWorld().getBlockAt(x, y, z);
								this.getBlockTypes()[i][j][k] = block.getTypeId();
								this.getBlockDatas()[i][j][k] = block.getData();
								BlockState state = block.getState();
								if(state instanceof Sign) {
									// Signs
									Sign sign = (Sign)state;
									if(sign.getLines() != null) {
										this.getSignLines().put("sign-" + i + "-" + j + "-" + k, sign.getLines());
									}
									
								} else if(state instanceof Chest) {
									// Chests
									Chest chest = (Chest)state;
									Inventory inv = chest.getInventory();
									int size = inv.getSize();
									List<ItemStack> items = new ArrayList<ItemStack>();
									for(int invIndex = 0; invIndex < size; invIndex++){
										ItemStack item = inv.getItem(invIndex);
										if(item != null && item.getType().getId() != Material.AIR.getId()) {
											items.add(item);
										}
									}
									this.getInvBlockContents().put("chest-" + i + "-" + j + "-" + k, items);
								} else if(state instanceof Dispenser) {
									// Dispensers							
									Dispenser dispenser = (Dispenser)state;
									Inventory inv = dispenser.getInventory();
									int size = inv.getSize();
									List<ItemStack> items = new ArrayList<ItemStack>();
									for(int invIndex = 0; invIndex < size; invIndex++){
										ItemStack item = inv.getItem(invIndex);
										if(item != null && item.getType().getId() != Material.AIR.getId()) {
											items.add(item);
										}
									}
									this.getInvBlockContents().put("dispenser-" + i + "-" + j + "-" + k, items);
								}
								
								noOfSavedBlocks++;
							} catch (Exception e) {
								System.out.println("Failed to save block in arena " + getName() + ". Saved blocks so far:" + noOfSavedBlocks 
										+ ". Error at x:" + x + " y:" + y + " z:" + z + ". Exception:" + e.getClass().toString() + e.getMessage());
								e.printStackTrace();
							} finally {
								z++;
							}
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to save arena " + getName() + " blocks. Saved blocks:" + noOfSavedBlocks 
					+ ". Error at x:" + x + " y:" + y + " z:" + z + ". Exception:" + e.getClass().toString() + " "+ e.getMessage());
			e.printStackTrace();
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
							try {
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
									//nicc start <-- Stop fire (Thunder spleef)
									if (world.getBlockAt(x, y, z).getType() == Material.FIRE) world.getBlockAt(x, y, z).setTypeId(0);
									if (world.getBlockAt(x+1, y, z).getType() == Material.FIRE) world.getBlockAt(x+1, y, z).setTypeId(0);
									if (world.getBlockAt(x-1, y, z).getType() == Material.FIRE) world.getBlockAt(x-1, y, z).setTypeId(0);
									if (world.getBlockAt(x, y+1, z).getType() == Material.FIRE) world.getBlockAt(x, y+1, z).setTypeId(0);
									if (world.getBlockAt(x, y-1, z).getType() == Material.FIRE) world.getBlockAt(x, y-1, z).setTypeId(0);
									if (world.getBlockAt(x, y, z+1).getType() == Material.FIRE) world.getBlockAt(x, y, z+1).setTypeId(0);
									if (world.getBlockAt(x, y, z-1).getType() == Material.FIRE) world.getBlockAt(x, y, z-1).setTypeId(0);
									//nicc end
										// regular block
										currentBlock.setType(Material.getMaterial(oldBlockType));
										currentBlock.setData(oldBlockData);
									}
									noOfResetBlocks++;
								visitedBlocks++;
							} catch (Exception e) {
								System.out.println("Failed to reset block in arena " + getName() + ". Visited blocks so far:" + visitedBlocks 
										+ ". Blocks reset: "+ noOfResetBlocks + 
										". Error at x:" + x + " y:" + y + " z:" + z + ". Exception:" + e.getClass().toString() + " " + e.getMessage());
								e.printStackTrace();
							} finally {
								z++;
							}
						}
						y++;
					}
					x++;
				}
			}		
		} catch (Exception e) {
			System.out.println("Failed to reset arena " + getName() + " blocks. Blocks visited: " + visitedBlocks 
					+ ". Blocks reset: "+ noOfResetBlocks + ". Error at x:" + x + " y:" + y + " z:" + z 
					+ ". Current block: " + currentBlockId + ". Old block: " + oldBlockType + ". Exception: " + e.getClass().toString() + " " + e.getMessage());
			e.printStackTrace();
		}
		return noOfResetBlocks;
	}

	public byte[][][] getBlockDatas() {
		return this.blockDatas;
	}
	
	public void setBlockDatas(byte[][][] data) {
		this.blockDatas = data;
	}
	

	public void setCornerTwo(Block block) {
		this.cornerTwo = new BlockInfo(block);
	}
	
	public Block getMinXBlock() {
		if(cornerOne.getX() < cornerTwo.getX()) return BlockInfo.getBlock(world, cornerOne);
		return BlockInfo.getBlock(world, cornerTwo);
	}
	
	public Block getMinYBlock() {
		if(cornerOne.getY() < cornerTwo.getY()) return BlockInfo.getBlock(world, cornerOne);
		return BlockInfo.getBlock(world, cornerTwo);
	}
	
	public Block getMinZBlock() {
		if(cornerOne.getZ() < cornerTwo.getZ()) return BlockInfo.getBlock(world, cornerOne);
		return BlockInfo.getBlock(world, cornerTwo);
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
		if(cornerOne.getX() < cornerTwo.getX()) return BlockInfo.getBlock(world, cornerTwo);
		return BlockInfo.getBlock(world, cornerOne);
	}
	
	public Block getMaxYBlock() {
		if(cornerOne.getY() < cornerTwo.getY()) return BlockInfo.getBlock(world, cornerTwo);
		return BlockInfo.getBlock(world, cornerOne);
	}
	
	public Block getMaxZBlock() {
		if(cornerOne.getZ() < cornerTwo.getZ()) return BlockInfo.getBlock(world, cornerTwo);
		return BlockInfo.getBlock(world, cornerOne);
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
		return BlockInfo.getBlock(world, cornerOne);
	}
	
	public Block getCornerTwo() {
		return BlockInfo.getBlock(world, cornerTwo);
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
			System.out.println("Failed to set block to " + material + "in arena " + name + "." + e.getClass().toString() + " " + e.getMessage());
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
			System.out.println("Failed to set block to " + material + "in arena " + name + "." + e.getClass().toString() + " " + e.getMessage());
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
			System.out.println("Failed to switch block to " + newType + "in arena " + name + "." + e.getClass().toString() + " " + e.getMessage());
		}
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

	public void setSignLines(HashMap<String, String[]> signLines) {
		this.signLines = signLines;
	}

	public HashMap<String, String[]> getSignLines() {
		return signLines;
	}

	public void setInvBlockContents(HashMap<String, List<ItemStack>> invBlockContents) {
		this.invBlockContents = invBlockContents;
	}

	public HashMap<String, List<ItemStack>> getInvBlockContents() {
		return invBlockContents;
	}

}