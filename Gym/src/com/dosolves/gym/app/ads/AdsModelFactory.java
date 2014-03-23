package com.dosolves.gym.app.ads;

import android.app.Activity;

import com.dosolves.gym.ads.AdsController;

public interface AdsModelFactory {

	AdsController createController(Activity activity);

}
