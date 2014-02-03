package com.dosolves.gym.domain.exercise.data;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;

public class ExerciseUpdaterImpl implements ExerciseUpdater {

	private DataAccess dataAccess;

	public ExerciseUpdaterImpl(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public void create(String newExerciseName,int categoryId) {
		Map<String, Object> keysAndvalues = createExerciseValues(newExerciseName, categoryId);
		
		dataAccess.create(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, keysAndvalues);
	}

	private Map<String, Object> createExerciseValues(String newExerciseName, int categoryId) {
		Map<String, Object> keysAndvalues = new HashMap<String, Object>();

		keysAndvalues.put(ExerciseStructureGiver.NAME_PROPERTY_NAME, newExerciseName);
		keysAndvalues.put(ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, categoryId);
		
		return keysAndvalues;
	}

	@Override
	public void delete(Exercise exerciseToBeDeleted) {
		dataAccess.delete(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.ID_PROPERTY_NAME, exerciseToBeDeleted.getId());
	}

}
