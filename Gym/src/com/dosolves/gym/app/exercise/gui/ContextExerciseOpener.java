package com.dosolves.gym.app.exercise.gui;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseOpener;

public class ContextExerciseOpener implements ExerciseOpener {

	private Context context;

	public ContextExerciseOpener(Context context) {
		this.context = context;
	}

	@Override
	public void openExercise(Exercise exercise) {
		Intent intent = new Intent(context, PerformanceActivity.class);
		intent.putExtra(PerformanceActivity.EXORCISE_BUNDLE_KEY, exercise);
		context.startActivity(intent);
	}

}
