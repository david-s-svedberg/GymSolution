package com.dosolves.gym.domain.category;

public class Category {
	
	private int id = -1;
	private String name = "";
	
	public Category(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}