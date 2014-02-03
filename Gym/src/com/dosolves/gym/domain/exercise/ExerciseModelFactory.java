package com.dosolves.gym.domain.exercise;

import com.dosolves.gym.app.exercise.gui.ExercisesActivity;

import android.content.Context;
import android.widget.ArrayAdapter;

public interface ExerciseModelFactory {

	ArrayAdapter<Exercise> createAdapter(Context context);
	ExerciseController createController(ExercisesActivity activity, ArrayAdapter<Exercise> adapter);

}
