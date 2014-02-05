package com.dosolves.gym.domain.performance;

import java.util.List;

import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.ReadyToGetDataCallback;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;


public class PerformanceController implements ReadyToGetDataCallback {

	private PerformanceAdapter adapter;
	private SetRetriever retriever;
	private PerformanceBuilder performanceBuilder;
	private CurrentExerciseHolder exerciseHolder;

	public PerformanceController(PerformanceAdapter adapter,
								 CurrentExerciseHolder exerciseHolder, 
								 SetRetriever retriever,
								 PerformanceBuilder performanceBuilder) {
		this.adapter = adapter;
		this.exerciseHolder = exerciseHolder;
		this.retriever = retriever;
		this.performanceBuilder = performanceBuilder;
	}

	@Override
	public void onReadyToGetData() {
		updatePerformances();
	}

	private void updatePerformances() {
		Exercise exercise = exerciseHolder.getCurrentExercise();
		List<Set> sets = retriever.getSetsInExercise(exercise);
		List<Performance> performances = performanceBuilder.build(sets);
		
		adapter.clear();
		adapter.setPerformances(performances);
		adapter.notifyDataSetChanged();
	}

}
