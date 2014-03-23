package com.dosolves.gym.app.gui;

import android.app.Activity;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.ViewSetter;

public class PerformaceViewSetter implements ViewSetter {

	private Activity activity;

	public PerformaceViewSetter(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void setAdsView() {
		activity.setContentView(R.layout.activity_exercise_input_with_ads);
	}

	@Override
	public void setAdsFreeView() {
		activity.setContentView(R.layout.activity_exercise_input);
	}

}
