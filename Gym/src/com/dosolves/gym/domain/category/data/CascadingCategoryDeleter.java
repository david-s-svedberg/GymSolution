package com.dosolves.gym.domain.category.data;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;

public class CascadingCategoryDeleter implements ItemDeleter {

	private ExerciseIdRetriever exerciseIdRetriever;
	private ItemDeleter exerciseDeleter;
	private CategoryUpdater categoryUpdater;

	public CascadingCategoryDeleter(ExerciseIdRetriever exerciseIdRetriever, 
									ItemDeleter exerciseDeleter, 
									CategoryUpdater categoryUpdater) {
		this.exerciseIdRetriever = exerciseIdRetriever;
		this.exerciseDeleter = exerciseDeleter;
		this.categoryUpdater = categoryUpdater;		
	}

	@Override
	public void deleteItem(int categoryId) {
		for(int exerciseId : exerciseIdRetriever.getIds(categoryId)){
			exerciseDeleter.deleteItem(exerciseId);
		}
		categoryUpdater.delete(categoryId);
	}

}
