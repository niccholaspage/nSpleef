package com.niccholaspage.nSpleef;

public enum DefaultProperty {
	LAVA_OUT(false);
	
	private final Object value;
	
	private DefaultProperty(Object value){
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public String toString(){
		return name().replace("_", "").toLowerCase();
	}
}
