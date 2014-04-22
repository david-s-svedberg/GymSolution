package com.dosolves.gym.domain.category.data;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;

public class CascadingCategoryDeleter implements ItemDeleter {

	private ExerciseIdRetriever exerciseIdRetriever;

	public CascadingCategoryDeleter(ExerciseIdRetriever exerciseIdRetriever) {
		this.exerciseIdRetriever = exerciseIdRetriever;
	}

	@Override
	public void deleteItem(int itemId) {

	}

}
