package com.dosolves.gym.app.database.exercise;

import java.util.List;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.ExerciseStructureGiver;

public class CursorExerciseRetriever implements ExerciseRetriever {

	private DataAccess dataAccess;
	private CursorExerciseFactory exerciseFactory;

	public CursorExerciseRetriever(DataAccess dataAccess, CursorExerciseFactory exerciseFactory) {
		this.dataAccess = dataAccess;
		this.exerciseFactory = exerciseFactory;

	}

	@Override
	public List<Exercise> getExercisesInCategory(Category category) {
		return exerciseFactory.CreateExercises(dataAccess.get(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, category.getId()));
	}

}
