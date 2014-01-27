package com.dosolves.gym.domain.exercise;

public interface ExerciseUpdater {

	void create(String newExerciseName, int categoryId);
	void delete(Exercise exercise);

}
