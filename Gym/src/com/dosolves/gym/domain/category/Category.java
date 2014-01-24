package com.dosolves.gym.domain.category;

import java.io.Serializable;

public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	@Override
	public boolean equals(Object o) {
		Category other = (Category)o;
		
		return other.name.equals(this.name) && other.id == this.id;
	}

}