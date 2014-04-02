package com.dosolves.gym.app.ads;

import com.dosolves.gym.app.performance.gui.PerformanceActivity;

import android.content.Context;
import android.content.Intent;


public class ContextRouterActivityStarter implements RouterActivityStarter {

	private Context context;

	public ContextRouterActivityStarter(Context context) {
		this.context = context;
	}

	@Override
	public void startRouterActivity() {
		Intent intent = new Intent(context, RouterActivity.class);
		context.startActivity(intent);
	}

}
