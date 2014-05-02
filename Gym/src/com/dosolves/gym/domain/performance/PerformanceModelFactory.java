package com.dosolves.gym.domain.performance;

import android.content.Context;

import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.app.gui.performance.ContextualMenuHandlerForSets;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;

public interface PerformanceModelFactory {

	PerformanceAdapter createAdapter(Context exercisesPerformanceActivity);
	PerformanceController createController(Context context, PerformanceAdapter adapter, CurrentExerciseHolder holder, FragmentManagerProvider fragmentManagerProvider);
	SetLastResultUseCaseControllerImpl createSetLastResultUseCaseController(PerformanceActivity performanceActivity);
	ContextualMenuHandlerForSets createContextHandler(PerformanceActivity activity);

}
