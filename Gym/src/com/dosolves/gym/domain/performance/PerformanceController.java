package com.dosolves.gym.domain.performance;

import java.util.List;

import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.ReadyToGetDataCallback;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;


public class PerformanceController implements ReadyToGetDataCallback, 
											  NewSetShouldBeCreatedCallback,
											  SetMenuRequestedCallback,
											  EditSetDialogRequestedCallback,
											  SetShouldBeEditedCallback,
											  SetShouldBeDeletedCallback{

	private PerformanceAdapter adapter;
	private SetRetriever retriever;
	private PerformanceBuilder performanceBuilder;
	private CurrentExerciseHolder exerciseHolder;
	private SetUpdater updater;
	private EditSetDialogShower editSetDialogShower;
	private SetMenuDialogShower setMenuDialogShower;

	public PerformanceController(PerformanceAdapter adapter,
								 CurrentExerciseHolder exerciseHolder, 
								 SetRetriever retriever,
								 PerformanceBuilder performanceBuilder, 
								 SetUpdater updater, 
								 EditSetDialogShower editSetDialogShower, 
								 SetMenuDialogShower setMenuDialogShower) {
		this.adapter = adapter;
		this.exerciseHolder = exerciseHolder;
		this.retriever = retriever;
		this.performanceBuilder = performanceBuilder;
		this.updater = updater;
		this.editSetDialogShower = editSetDialogShower;
		this.setMenuDialogShower = setMenuDialogShower;
	}

	@Override
	public void onReadyToGetData() {
		updatePerformances();
	}
	
	@Override
	public void onNewSetShouldBeCreated(int reps, double weight) {
		updater.create(exerciseHolder.getCurrentExercise().getId(), reps, weight);
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

	@Override
	public void onEditSetDialogRequested(Set set) {
		editSetDialogShower.show(set, this);
	}

	@Override
	public void onSetShouldBeUpdated(Set set, int newReps, double newWeight) {
		updater.update(set, newReps, newWeight);
		updatePerformances();
	}

	@Override
	public void onSetShouldBeDeleted(Set setToBeDeleted) {
		updater.delete(setToBeDeleted);
		updatePerformances();
	}

	@Override
	public void onSetMenuRequested(Set set) {
		setMenuDialogShower.show(set, this, this);
	}

}
