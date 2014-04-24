package com.dosolves.gym.app.gui;

import android.app.Activity;
import android.app.DialogFragment;

import com.dosolves.gym.app.ads.RouterActivityCreatedListener;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserResponseListener;

public class UserAskerImpl implements UserAsker, RouterActivityCreatedListener {

	private RouterActivityStarter routerActivityStarter;
	private DialogFragment dialog;
	private UserResponseListener currentResponseListener;
	private RouteModule module;

	public UserAskerImpl(RouterActivityStarter routerActivityStarter, DialogFragment dialog, RouteModule module) {
		this.routerActivityStarter = routerActivityStarter;
		this.dialog = dialog;
		this.module = module;
	}

	@Override
	public void shouldParentItemBeDeleted(UserResponseListener responseListener) {
		this.currentResponseListener = responseListener;
		routerActivityStarter.startRouterActivity(RouteReason.FOR_DELETE_DIALOG, module);
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
