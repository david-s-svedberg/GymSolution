package com.dosolves.gym.app.performance.gui;

import com.dosolves.gym.domain.performance.Set;

public interface UserGestureListener {
	
	void onNewSetShouldBeCreated(int reps, double weight);
	void onSetShouldBeUpdated(Set set, int newReps, double newWeight);
	
}
