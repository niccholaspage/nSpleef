package com.niccholaspage.nSpleef;

public class nSpleefGame {
	private final String name;
	private final String arena;
	private final String owner;
	private double money = 0.0;
	private int mode = 0;
	public nSpleefGame(String name, String arena, String owner){
		this.name = name;
		this.arena = arena;
		this.owner = owner;
	}
	public String getName(){
		return name;
	}
	public String getArena(){
		return arena;
	}
	public String getOwner(){
		return owner;
	}
	public double getMoney(){
		return money;
	}
	public void setMoney(double money){
		this.money = money;
	}
	public int getMode(){
		return mode;
	}
	public void setMode(int mode){
		this.mode = mode;
	}
	public String toString(){
		return name + "," + arena + "," + owner + "," + money + "," + mode;
	}
}
