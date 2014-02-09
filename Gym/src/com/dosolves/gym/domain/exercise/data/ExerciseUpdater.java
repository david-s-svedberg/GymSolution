package com.dosolves.gym.domain.exercise.data;

import com.dosolves.gym.domain.exercise.Exercise;

public interface ExerciseUpdater {

	void create(String newExerciseName, int categoryId);
	void delete(Exercise exercise);
	void rename(Exercise exercise, String newExerciseName);

}
