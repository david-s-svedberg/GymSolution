package com.dosolves.gym.domain.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.category.Category;

public interface ExerciseModelFactory {

	ArrayAdapter<Exercise> createAdapter(Context context);

	ExerciseController createController(Context context, ArrayAdapter<Category> adapter);

}
