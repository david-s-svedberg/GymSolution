package com.dosolves.gym.domain;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.app.SystemEventListener;

public class SystemEventObservableImpl implements SystemEventObservable {
	
	private List<SystemEventListener> systemEventListeners;

	public SystemEventObservableImpl() {
		this.systemEventListeners = new ArrayList<SystemEventListener>();
	}
	
	@Override
	public void registerSystemEventListener(SystemEventListener listener){
		systemEventListeners.add(listener);
	}
	
	@Override
	public void notifyUIAboutToBeCreated(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onUIAboutToBeCreated();
	}
	
	@Override
	public void notifyMenuShouldBeCreated(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onMenuShouldBeCreated();
	}
	
	@Override
	public void notifyUICreated(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onUICreated();
	}
	
	@Override
	public void notifyUIDestroyed(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onUIDestroyed();
	}
	
	@Override
	public void notifyUIHidden(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onUIHidden();
	}
	
	@Override
	public void notifyUIInteractive(){
		for(SystemEventListener listener:systemEventListeners)
			listener.onUIInteractive();
	}
	
}