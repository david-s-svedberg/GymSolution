package com.dosolves.gym.app.ads;

import android.app.Activity;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.AdViewStateHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdViewStateHandlerImpl implements AdViewStateHandler {

	private Activity activity;
	private AdView ad;

	public AdViewStateHandlerImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void init() {
		ad = (AdView)activity.findViewById(R.id.adView);
		
		AdRequest request = new AdRequest.Builder()
		.addKeyword("gym")
		.addKeyword("weights")
		.addKeyword("protein")
		.addKeyword("sport")
		.addTestDevice("7D817564133AD09CA8BB91602A4E5BB0")
		.build();
		
		ad.loadAd(request);
	}

	@Override
	public void pause() {
		ad.pause();
	}

	@Override
	public void resume() {
		ad.resume();
	}

	@Override
	public void destroy() {
		ad.destroy();
	}

}
