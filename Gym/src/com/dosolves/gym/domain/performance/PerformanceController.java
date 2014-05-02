package com.dosolves.gym.domain.performance;

import java.util.List;

import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.UserRequestListener;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;


public class PerformanceController implements NewSetShouldBeCreatedCallback,
											  SetShouldBeEditedCallback,
											  UserRequestListener, 
											  SystemEventListener{
	
	private PerformanceAdapter adapter;
	private SetRetriever retriever;
	private PerformanceBuilder performanceBuilder;
	private CurrentExerciseHolder exerciseHolder;
	private SetUpdater updater;
	private EditSetDialogShower editSetDialogShower;
	private ItemDeleter setDeleter;

	public PerformanceController(PerformanceAdapter adapter,
								 CurrentExerciseHolder exerciseHolder, 
								 SetRetriever retriever,
								 PerformanceBuilder performanceBuilder, 
								 SetUpdater updater, 
								 EditSetDialogShower editSetDialogShower, 
								 ItemDeleter setDeleter) {
		this.adapter = adapter;
		this.exerciseHolder = exerciseHolder;
		this.retriever = retriever;
		this.performanceBuilder = performanceBuilder;
		this.updater = updater;
		this.editSetDialogShower = editSetDialogShower;
		this.setDeleter = setDeleter;
	}
	
	@Override
	public void onNewSetShouldBeCreated(int reps, double weight) {
		updater.create(exerciseHolder.getCurrentExercise().getId(), reps, weight);
		updatePerformances();
	}

	private void updatePerformances() {
		Exercise exercise = exerciseHolder.getCurrentExercise();
		List<Set> sets = retriever.getSetsInExercise(exercise.getId());
		List<Performance> performances = performanceBuilder.build(sets);
		
		adapter.clear();
		adapter.setPerformances(performances);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSetShouldBeUpdated(Set set, int newReps, double newWeight) {
		updater.update(set, newReps, newWeight);
		updatePerformances();
	}

	@Override
	public void deleteItems(List<Integer> ids) {
		for(int id:ids)
			setDeleter.deleteItem(id);
		
		updatePerformances();
	}

	@Override
	public void editItem(Integer id) {
		editSetDialogShower.show(retriever.getSet(id), this);
	}
	
	@Override
	public void onUIInteractive() {
		updatePerformances();
	}
	
	@Override
	public void onUIAboutToBeCreated() {}
	@Override
	public void onUICreated() {}
	@Override
	public void onMenuShouldBeCreated() {}
	@Override
	public void onUIHidden() {}
	@Override
	public void onUIDestroyed() {}
	
}
