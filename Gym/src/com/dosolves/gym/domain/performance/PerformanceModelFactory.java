package com.dosolves.gym.domain.performance;

import com.dosolves.gym.app.gui.performance.PerformanceActivity;
import com.dosolves.gym.app.gui.performance.PerformanceAdapter;

public interface PerformanceModelFactory {

	PerformanceAdapter createAdapter(PerformanceActivity exercisesPerformanceActivity);
	PerformanceController createController(PerformanceActivity exercisesPerformanceActivity, PerformanceAdapter exercisePerformanceAdapter);

}
