package com.dosolves.gym.domain.exercise;

import java.io.Serializable;

public class Exercise implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int categoryId;
	private String name;

	public Exercise(int id, int categoryId, String name) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		Exercise other = (Exercise)o;
		
		return other.name.equals(this.name) && 
			   other.id == this.id && 
			   other.categoryId == this.categoryId;
	}

}
