package com.dosolves.gym.app.gui.exercise;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.app.gui.input.ExerciseInputActivity;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseOpener;

public class ContextExerciseOpener implements ExerciseOpener {

	private Context context;

	public ContextExerciseOpener(Context context) {
		this.context = context;
	}

	@Override
	public void openExercise(Exercise exercise) {
		Intent intent = new Intent(context, ExerciseInputActivity.class);
		intent.putExtra(ExerciseInputActivity.EXORCISE_BUNDLE_KEY, exercise);
		context.startActivity(intent);
	}

}
