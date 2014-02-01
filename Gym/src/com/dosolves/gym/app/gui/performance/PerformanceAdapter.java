package com.dosolves.gym.app.gui.performance;

import com.dosolves.gym.domain.performance.ExercisePerformance;

import android.content.Context;
import android.widget.ArrayAdapter;

public class PerformanceAdapter extends ArrayAdapter<ExercisePerformance>{

	public PerformanceAdapter(Context context, int resource) {
		super(context, resource);
	}

}
