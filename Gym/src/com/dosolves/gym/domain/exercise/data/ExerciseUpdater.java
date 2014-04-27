package com.dosolves.gym.domain.exercise.data;

public interface ExerciseUpdater {

	void create(String newExerciseName, int categoryId);
	void delete(int exerciseId);
	void rename(int id, String newExerciseName);

}
