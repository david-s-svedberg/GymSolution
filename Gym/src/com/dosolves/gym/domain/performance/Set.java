package com.dosolves.gym.domain.performance;

import java.io.Serializable;
import java.util.Date;
public class Set implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int exerciseId;
	private int reps;
	private double weight;
	private Date date;

	public Set(int id, int exerciseId, int reps, double weight, Date date) {
		this.id = id;
		this.exerciseId = exerciseId;
		this.reps = reps;
		this.weight = weight;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public int getReps() {
		return reps;
	}

	public double getWeight() {
		return weight;
	}

	public Date getDate() {
		return date;
	}

}
