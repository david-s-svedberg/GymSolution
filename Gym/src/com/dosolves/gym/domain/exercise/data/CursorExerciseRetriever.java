package com.dosolves.gym.domain.exercise.data;

import java.util.List;

import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;

public class CursorExerciseRetriever implements ExerciseRetriever {

	private DataAccess dataAccess;
	private CursorExerciseFactory exerciseFactory;

	public CursorExerciseRetriever(DataAccess dataAccess, CursorExerciseFactory exerciseFactory) {
		this.dataAccess = dataAccess;
		this.exerciseFactory = exerciseFactory;

	}

	@Override
	public List<Exercise> getExercisesInCategory(int categoryId) {
		GymCursor cursor = dataAccess.get(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, categoryId);
		return exerciseFactory.CreateExercises(cursor);
	}

}
