package com.dosolves.gym.app.ads;

import com.dosolves.gym.ads.AdsRemovalBoughtStorer;
import com.dosolves.gym.app.PreferenceRetriever;

public class PreferensesAdsRemovalBoughtStorer implements AdsRemovalBoughtStorer {

	private PreferenceRetriever preferenceRetriver;

	public PreferensesAdsRemovalBoughtStorer(PreferenceRetriever preferenceRetriver) {
		this.preferenceRetriver = preferenceRetriver;
	}

	@Override
	public void storeThatAdsRemovalHasBeenBought() {
		preferenceRetriver.getPreferences().edit().putBoolean("showAds", false);
	}

}
