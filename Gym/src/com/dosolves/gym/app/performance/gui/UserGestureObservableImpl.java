package com.dosolves.gym.app.performance.gui;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.domain.performance.Set;

public class UserGestureObservableImpl implements UserGestureObservable {

	List<UserGestureListener> listeners;

	public UserGestureObservableImpl(){
		listeners = new ArrayList<UserGestureListener>();
	}
	
	@Override
	public void registerUserGestureListener(UserGestureListener listener) {
	listeners.add(listener);	
	}

	@Override
	public void notifyNewSetShouldBeCreated(int reps, double weight) {
		for(UserGestureListener listener:listeners){
			listener.onNewSetShouldBeCreated(reps, weight);
		}
	}

	@Override
	public void notifySetShouldBeUpdated(Set set, int newReps, double newWeight) {
		for(UserGestureListener listener:listeners){
			listener.onSetShouldBeUpdated(set, newReps, newWeight);
		}
	}
	
}
