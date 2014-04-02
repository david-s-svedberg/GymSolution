package com.dosolves.gym.app.ads;

import android.app.Activity;
import android.content.Context;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.inappbilling.IabHelper;

public interface AdsModelFactory {

	AdsController createController(Activity activity);
	AdsRemovalBuyerAdapter getAdsRemovalBuyer(Context context);
	IabHelper getIabHelper(Context context);

}
