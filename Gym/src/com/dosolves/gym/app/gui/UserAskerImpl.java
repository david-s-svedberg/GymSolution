package com.dosolves.gym.app.gui;

import android.app.Activity;
import android.app.DialogFragment;

import com.dosolves.gym.app.ads.RouterActivityCreatedListener;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserResponseListener;

public class UserAskerImpl implements UserAsker, RouterActivityCreatedListener {

	private RouterActivityStarter routerActivityStarter;
	private DialogFragment dialog;
	private UserResponseListener currentResponseListener;
	private RouteDialog module;
	private RouteReason reason;

	public UserAskerImpl(RouterActivityStarter routerActivityStarter, DialogFragment dialog, RouteReason reason,RouteDialog module) {
		this.routerActivityStarter = routerActivityStarter;
		this.dialog = dialog;
		this.module = module;
		this.reason = reason;
	}

	@Override
	public void askUser(UserResponseListener responseListener) {
		this.currentResponseListener = responseListener;
		routerActivityStarter.startRouterActivity(reason, module);
	}

	@Override
	public void onRouterActivityCreated(Activity activity) {
		dialog.show(activity.getFragmentManager(), "Tag");
	}

	@Override
	public UserResponseListener getCurrentResponseListener() {
		return currentResponseListener;
	}
	
}
