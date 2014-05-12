package com.dosolves.gym.domain.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.UserAsker;

public interface ExerciseModelFactory {

	ArrayAdapter<Exercise> createAdapter(Context context);
	ExerciseController createController(Context context, ArrayAdapter<Exercise> adapter, CurrentCategoryHolder holder);
	UserAsker getUserAsker();
	UserAsker createUserAsker(Context context);

}
