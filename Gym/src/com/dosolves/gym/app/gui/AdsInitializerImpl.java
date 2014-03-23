package com.dosolves.gym.app.gui;

import android.app.Activity;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.AdsInitializer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdsInitializerImpl implements AdsInitializer {

	private Activity activity;

	public AdsInitializerImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		AdView ad = (AdView)activity.findViewById(R.id.adView);
		
		AdRequest request = new AdRequest.Builder()
//		.addKeyword("gym")
//		.addKeyword("weights")
//		.addKeyword("protein")
//		.addKeyword("sport")
		.addTestDevice("7D817564133AD09CA8BB91602A4E5BB0")
		.build();
		
		ad.loadAd(request);
	}

}
