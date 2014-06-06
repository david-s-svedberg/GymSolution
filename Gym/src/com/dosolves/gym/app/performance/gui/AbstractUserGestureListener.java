package com.dosolves.gym.app.performance.gui;

import com.dosolves.gym.domain.performance.Set;

public class AbstractUserGestureListener implements UserGestureListener {

	@Override
	public void onNewSetShouldBeCreated(int reps, double weight) {}
	@Override
	public void onSetShouldBeUpdated(Set set, int newReps, double newWeight) {}

}
