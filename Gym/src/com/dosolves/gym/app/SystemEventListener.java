package com.dosolves.gym.app;

public interface SystemEventListener {

	void onUIAboutToBeCreated();
	void onUICreated();
	void onMenuShouldBeCreated();
	void onUIHidden();
	void onUIInteractive();
	void onUIDestroyed();
	
}
