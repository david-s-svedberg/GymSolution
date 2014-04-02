package com.dosolves.gym.app.ads;

import com.dosolves.gym.ads.AdsRemovalBoughtStorer;
import com.dosolves.gym.ads.AdsRemovalPurchasedListener;
import com.dosolves.gym.ads.ApplicationRestarter;
import com.dosolves.gym.ads.UserThanker;

public class AdsRemovalBoughtController implements AdsRemovalPurchasedListener {

	private AdsRemovalBoughtStorer adsRemovalBoughtStorer;
	private UserThanker userThanker;
	private ApplicationRestarter applicationRestarter;

	public AdsRemovalBoughtController(AdsRemovalBoughtStorer adsRemovalBoughtStorer, UserThanker userThanker, ApplicationRestarter applicationRestarter) {
		this.adsRemovalBoughtStorer = adsRemovalBoughtStorer;
		this.userThanker = userThanker;
		this.applicationRestarter = applicationRestarter;
	}

	@Override
	public void onAdsRemovalPurchased() {
		adsRemovalBoughtStorer.storeThatAdsRemovalHasBeenBought();
		userThanker.thankUser();
		applicationRestarter.restart();
	}

}
