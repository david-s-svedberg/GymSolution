package com.dosolves.gym.domain.exercise.data;

import java.util.List;

import com.dosolves.gym.domain.exercise.Exercise;


public class HighLevelExerciseIdRetriever implements ExerciseIdRetriever {

	private ExerciseRetriever exerciseRetriever;

	public HighLevelExerciseIdRetriever(ExerciseRetriever exerciseRetriever) {
		this.exerciseRetriever = exerciseRetriever;
	}

	@Override
	public int[] getIds(int categoryId) {
		List<Exercise> exercises = exerciseRetriever.getExercisesInCategory(categoryId);
		int[] ids = new int[exercises.size()];
		
		for(int i = 0;i<exercises.size();i++){
			ids[i] = exercises.get(i).getId();
		}
		
		return ids;
	}

}
