package com.dosolves.gym.app.ads;

import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;

import android.content.Context;
import android.content.Intent;


public class ContextRouterActivityStarter implements RouterActivityStarter {

	private Context context;

	public ContextRouterActivityStarter(Context context) {
		this.context = context;
	}

	@Override
	public void startRouterActivity(RouteReason reason, RouteModule module) {
		Intent intent = new Intent(context, RouterActivity.class);
		intent.putExtra(RouterActivity.REASON_KEY, reason);
		intent.putExtra(RouterActivity.MODULE_KEY, module);
		
		context.startActivity(intent);
	}

}
