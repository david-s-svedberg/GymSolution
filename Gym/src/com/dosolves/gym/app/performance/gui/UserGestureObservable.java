package com.dosolves.gym.app.performance.gui;

import com.dosolves.gym.domain.performance.Set;


public interface UserGestureObservable {
	
	public void registerUserGestureListener(UserGestureListener listener);

	public void notifyNewSetShouldBeCreated(int reps, double weight);
	public void notifySetShouldBeUpdated(Set set, int newReps, double newWeight);

}
