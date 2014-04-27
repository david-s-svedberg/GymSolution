package com.dosolves.gym.domain.exercise.data;

import java.util.List;

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
		return exerciseFactory.CreateExercises(dataAccess.get(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, categoryId));
	}

	@Override
	public Exercise getExercise(int id) {
		return exerciseFactory.CreateExercise(dataAccess.get(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.ID_PROPERTY_NAME, id));
	}

}
