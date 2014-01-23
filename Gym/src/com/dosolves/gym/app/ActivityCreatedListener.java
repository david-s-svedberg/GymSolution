package com.dosolves.gym.app;

import com.dosolves.gym.domain.ModelComposer;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

public class ActivityCreatedListener implements ActivityLifecycleCallbacks {

	private ModelComposer composer;
	public ActivityCreatedListener(ModelComposer composer) {
		this.composer = composer;
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		composer.compose(activity);
	}

	@Override
	public void onActivityDestroyed(Activity activity) {}
	@Override
	public void onActivityPaused(Activity activity) {}
	@Override
	public void onActivityResumed(Activity activity) {}
	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
	@Override
	public void onActivityStarted(Activity activity) {}
	@Override
	public void onActivityStopped(Activity activity) {}

}
