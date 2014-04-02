package com.dosolves.gym.app.ads;

import android.app.Activity;

import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.AdsRemovalPurchasedListener;
import com.dosolves.gym.inappbilling.IabHelper;
import com.dosolves.gym.inappbilling.IabHelper.OnIabPurchaseFinishedListener;
import com.dosolves.gym.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.dosolves.gym.inappbilling.IabResult;
import com.dosolves.gym.inappbilling.Purchase;

public class AdsRemovalBuyerAdapter implements AdsRemovalBuyer, RouterActivityCreatedListener, OnIabPurchaseFinishedListener {

	private static final int REQUEST_CODE = 53452;
	private static final String REMOVE_ADS = "remove_ads";
	
	private boolean inAppBillingAvailable = false;
	
	private IabHelper helper;
	private RouterActivityStarter routerActivityStarter;
	private UserSpecificPayloadGenerator userSpecificPayloadGenerator;
	private AdsRemovalPurchasedListener adsRemovalPurchasedListener;

	public AdsRemovalBuyerAdapter(IabHelper helper, 
								  RouterActivityStarter routerActivityStarter, 
								  UserSpecificPayloadGenerator userSpecificPayloadGenerator,
								  AdsRemovalPurchasedListener adsRemovalPurchasedListener) {
		this.helper = helper;
		this.routerActivityStarter = routerActivityStarter;
		this.userSpecificPayloadGenerator = userSpecificPayloadGenerator;
		this.adsRemovalPurchasedListener = adsRemovalPurchasedListener;
		runSetup();
	}

	private void runSetup() {
		helper.startSetup(new OnIabSetupFinishedListener() {
			
			@Override
			public void onIabSetupFinished(IabResult result) {
				if(result.isSuccess())
					inAppBillingAvailable = true;
			}
		});
	}
	
	private void rerunSetupAndTryInitiatePurchaseSequence() {
		helper.startSetup(new OnIabSetupFinishedListener() {
			
			@Override
			public void onIabSetupFinished(IabResult result) {
				if(result.isSuccess()){
					inAppBillingAvailable = true;
					initiatePurchaseSequence();
				}	
			}
		});
	}

	@Override
	public void buyAdsRemoval() {
		if(!inAppBillingAvailable){
			rerunSetupAndTryInitiatePurchaseSequence();
		}
		else{
			initiatePurchaseSequence();
		}
	}

	protected void initiatePurchaseSequence() {
		routerActivityStarter.startRouterActivity();
	}

	@Override
	public void onRouterActivityCreated(Activity activity) {
		helper.launchPurchaseFlow(activity, REMOVE_ADS, REQUEST_CODE, this, userSpecificPayloadGenerator.generateUserSpecificPayload());
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		if(result.isSuccess()){
			adsRemovalPurchasedListener.onAdsRemovalPurchased();
		}
	}

}
