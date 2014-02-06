package com.dosolves.gym.domain.exercise;

import com.dosolves.gym.domain.CurrentCategoryHolder;

import android.content.Context;
import android.widget.ArrayAdapter;

public interface ExerciseModelFactory {

	ArrayAdapter<Exercise> createAdapter(Context context);
	ExerciseController createController(Context context, ArrayAdapter<Exercise> adapter, CurrentCategoryHolder holder);

}
