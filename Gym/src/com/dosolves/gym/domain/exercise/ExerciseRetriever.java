package com.dosolves.gym.domain.exercise;

import java.util.List;

import com.dosolves.gym.domain.category.Category;

public interface ExerciseRetriever {

	List<Exercise> getExercisesInCategory(Category category);

}
