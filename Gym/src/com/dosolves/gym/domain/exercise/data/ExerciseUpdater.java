package com.dosolves.gym.domain.exercise.data;

public interface ExerciseUpdater extends ExerciseCreator {

	void delete(int exerciseId);
	void rename(int id, String newExerciseName);

}
