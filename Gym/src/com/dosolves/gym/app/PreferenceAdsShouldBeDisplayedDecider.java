package com.dosolves.gym.app;

import com.dosolves.gym.ads.AdsShouldBeDisplayedDecider;

public class PreferenceAdsShouldBeDisplayedDecider implements AdsShouldBeDisplayedDecider{

	private PreferenceRetriever retriever;

	public PreferenceAdsShouldBeDisplayedDecider(PreferenceRetriever retriever) {
		this.retriever = retriever;
	}

	@Override
	public boolean adsShouldBeDisplayed() {
		return retriever.getPreferences().getBoolean("showAds", true);		
	}

}
