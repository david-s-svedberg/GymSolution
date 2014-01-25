package com.dosolves.gym.domain.exercise;

public class Exercise {

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

}
