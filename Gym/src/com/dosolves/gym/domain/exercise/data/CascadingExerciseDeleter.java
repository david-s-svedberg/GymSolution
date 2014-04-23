package com.dosolves.gym.domain.exercise.data;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.performance.data.SetIdRetriever;

public class CascadingExerciseDeleter implements ItemDeleter {

	private SetIdRetriever setIdRetriever;
	private ItemDeleter setDeleter;
	private ExerciseUpdater exerciseUpdater;

	public CascadingExerciseDeleter(SetIdRetriever setIdRetriever, ItemDeleter setDeleter, ExerciseUpdater exerciseUpdater) {
		this.setIdRetriever = setIdRetriever;
		this.setDeleter = setDeleter;
		this.exerciseUpdater = exerciseUpdater;
	}

	@Override
	public void deleteItem(int exerciseId) {
		for(int setId : setIdRetriever.getIds(exerciseId)){
			setDeleter.deleteItem(setId);
		}
		exerciseUpdater.delete(exerciseId);
	}

}
