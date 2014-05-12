package com.dosolves.gym.domain.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dosolves.gym.domain.exercise.ExerciseTemplate;

public class CategoryTemplate {

	private String name;
	private List<ExerciseTemplate> exercises;
	
	public CategoryTemplate(String name) {
		this.name = name;
		this.exercises = new ArrayList<ExerciseTemplate>();
	}

	public String getName() {
		return name;
	}

	public void addExercise(ExerciseTemplate exercise) {
		exercises.add(exercise);
	}

	public List<ExerciseTemplate> getExercises() {
		return Collections.unmodifiableList(exercises);
	}

}
