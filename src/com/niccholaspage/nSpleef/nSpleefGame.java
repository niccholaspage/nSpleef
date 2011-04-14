package com.niccholaspage.nSpleef;

public class nSpleefGame {
	private final String name;
	private final String arena;
	private final String owner;
	private int money = 0;
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
	public int getMoney(){
		return money;
	}
	public void setMoney(int money){
		this.money = money;
	}
}
