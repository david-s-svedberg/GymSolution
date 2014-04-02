package com.dosolves.gym.app.ads;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.ads.ApplicationRestarter;

public class ContextApplicationRestarter implements ApplicationRestarter {

	private Context context;

	public ContextApplicationRestarter(Context context) {
		this.context = context;
	}

	@Override
	public void restart() {
		Context applicationContext = context.getApplicationContext();
		Intent restartIntent = applicationContext.getPackageManager().getLaunchIntentForPackage(applicationContext.getPackageName());
		restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		applicationContext.startActivity(restartIntent);
	}

}
