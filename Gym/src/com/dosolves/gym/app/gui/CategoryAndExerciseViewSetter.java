package com.dosolves.gym.app.gui;

import android.app.Activity;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.ViewSetter;

public class CategoryAndExerciseViewSetter implements ViewSetter {

	private Activity activity;

	public CategoryAndExerciseViewSetter(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void setAdsView() {
		activity.setContentView(R.layout.user_updateable_items_with_ads);
	}

	@Override
	public void setAdsFreeView() {
		//Do nothing
	}

}
