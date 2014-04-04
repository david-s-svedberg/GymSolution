package com.dosolves.gym.app.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dosolves.gym.inappbilling.ActivityResultListener;

public class RouterActivity extends Activity {

	private RouterActivityCreatedListener routerActivityCreatedListener;
	private ActivityResultListener activityResultListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		routerActivityCreatedListener.onRouterActivityCreated(this);
	}

	public void setRouterActivityCreatedListener(RouterActivityCreatedListener routerActivityCreatedListener) {
		this.routerActivityCreatedListener = routerActivityCreatedListener;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		activityResultListener.onActivityResult(requestCode, resultCode, data);
	}

	public void setActivityResultListener(ActivityResultListener activityResultListener) {
		this.activityResultListener = activityResultListener;
	}
}
