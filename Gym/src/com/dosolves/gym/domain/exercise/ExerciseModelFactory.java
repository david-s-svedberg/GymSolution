package com.dosolves.gym.domain.exercise;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.domain.CurrentCategoryHolder;

public interface ExerciseModelFactory {

	ArrayAdapter<Exercise> createAdapter(Context context);
	ExerciseController createController(Context context, ArrayAdapter<Exercise> adapter, CurrentCategoryHolder holder);
	UserAskerImpl getUserAsker();
	UserAskerImpl createUserAsker(Activity activity);

}
