package com.dosolves.gym.domain;

import com.dosolves.gym.app.SystemEventListener;

public interface SystemEventObservable {

	public void registerSystemEventListener(SystemEventListener listener);

	public void notifyUIAboutToBeCreated();

	public void notifyMenuShouldBeCreated();

	public void notifyUICreated();

	public void notifyUIDestroyed();

	public void notifyUIHidden();

	public void notifyUIInteractive();

}