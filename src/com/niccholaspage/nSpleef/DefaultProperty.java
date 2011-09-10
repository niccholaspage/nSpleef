package com.niccholaspage.nSpleef;

public enum DefaultProperty {
	LAVA_OUT("false");
	
	private final String value;
	
	private DefaultProperty(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	public String toString(){
		return name().replace("_", "").toLowerCase();
	}
}
