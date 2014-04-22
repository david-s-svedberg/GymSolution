package com.dosolves.gym.app.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dosolves.gym.inappbilling.ActivityResultListener;

public class RouterActivity extends Activity {

	public enum RouteModule {
		CATEGORY,
		EXERCISE,
		NONE
	}

	public enum RouteReason{
		FOR_DELETE_DIALOG, 
		FOR_IN_APP_BILLING
	}

	public static final String REASON_KEY = "REASON_KEY";
	public static final String MODULE_KEY = "MODULE_KEY";
	
	private RouterActivityCreatedListener routerActivityCreatedListener;
	private ActivityResultListener activityResultListener;

	private Object dialogResultListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		routerActivityCreatedListener.onRouterActivityCreated(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		activityResultListener.onActivityResult(requestCode, resultCode, data);
	}
	
	public void setRouterActivityCreatedListener(RouterActivityCreatedListener routerActivityCreatedListener) {
		this.routerActivityCreatedListener = routerActivityCreatedListener;
	}

	public void setActivityResultListener(ActivityResultListener activityResultListener) {
		this.activityResultListener = activityResultListener;
	}

	public void setDialogResultListener(Object dialogResultListener) {
		this.dialogResultListener = dialogResultListener;
	}

	public Object getDialogResultListener() {
		return dialogResultListener;
	}
	
}
