package com.dosolves.gym.app.gui.exercise;

import android.content.Context;

import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseOpener;

public class ContextExerciseOpener implements ExerciseOpener {

	private Context context;

	public ContextExerciseOpener(Context context) {
		this.context = context;
	}

	@Override
	public void openExercise(Exercise exerciseMock) {
		// TODO Auto-generated method stub
	}

}
