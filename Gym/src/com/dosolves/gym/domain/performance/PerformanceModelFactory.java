package com.dosolves.gym.domain.performance;

import android.content.Context;

import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;

public interface PerformanceModelFactory {

	PerformanceAdapter createAdapter(Context exercisesPerformanceActivity);
	PerformanceController createController(Context context, PerformanceAdapter adapter, CurrentExerciseHolder holder, FragmentManagerProvider fragmentManagerProvider);

}
