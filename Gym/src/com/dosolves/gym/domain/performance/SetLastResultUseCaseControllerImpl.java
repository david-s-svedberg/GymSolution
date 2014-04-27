package com.dosolves.gym.domain.performance;

import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.performance.data.SetRetriever;

public class SetLastResultUseCaseControllerImpl implements SetLastResultUseCaseController, SystemEventListener {

	private CurrentExerciseHolder currentExerciseHolder;
	private SetRetriever setRetriever;
	private StartValueSetter startValueSetter;

	public SetLastResultUseCaseControllerImpl(CurrentExerciseHolder currentExerciseHolder,
											  SetRetriever setRetriever, 
											  StartValueSetter startValueSetter) {
		this.currentExerciseHolder = currentExerciseHolder;
		this.setRetriever = setRetriever;
		this.startValueSetter = startValueSetter;
	}

	@Override
	public void onUICreated() {
		Set lastSetForExercise = setRetriever.getLastSetForExercise(currentExerciseHolder.getCurrentExercise());
		
		if(lastSetForExercise != null)
			startValueSetter.setStartValues(lastSetForExercise);
	}
	
	@Override
	public void onUIAboutToBeCreated() {} // Ignore
	@Override
	public void onMenuShouldBeCreated() {} // Ignore
	@Override
	public void onUIHidden() {} // Ignore
	@Override
	public void onUIInteractive() {} //Ignore
	@Override
	public void onUIDestroyed() {} //Ignore
	
}
