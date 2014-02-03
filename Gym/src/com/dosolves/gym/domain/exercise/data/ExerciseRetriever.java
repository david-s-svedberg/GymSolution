package com.dosolves.gym.domain.exercise.data;

import java.util.List;

import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;

public interface ExerciseRetriever {

	List<Exercise> getExercisesInCategory(Category category);

}
