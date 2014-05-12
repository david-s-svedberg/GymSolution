package com.dosolves.gym.app.ads;

import android.app.Activity;

import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.AdsRemovalPurchasedListener;
import com.dosolves.gym.ads.UserSpecificPayloadValidator;
import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.inappbilling.IabHelper;
import com.dosolves.gym.inappbilling.IabHelper.OnIabPurchaseFinishedListener;
import com.dosolves.gym.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.dosolves.gym.inappbilling.IabResult;
import com.dosolves.gym.inappbilling.Purchase;

public class AdsRemovalBuyerAdapter implements AdsRemovalBuyer, RouterActivityCreatedListener, OnIabPurchaseFinishedListener {

	protected static final int REQUEST_CODE = 53452;
	
	private static final String REMOVE_ADS_SKU = "remove_ads";
	
	private boolean inAppBillingAvailable = false;
	
	protected IabHelper helper;
	protected RouterActivityStarter routerActivityStarter;
	protected UserSpecificPayloadGenerator userSpecificPayloadGenerator;
	protected AdsRemovalPurchasedListener adsRemovalPurchasedListener;
	protected UserSpecificPayloadValidator userSpecificPayloadValidator;

	protected Activity routerActivity;

	protected boolean purchaseUnderway;

	public AdsRemovalBuyerAdapter(IabHelper helper, 
								  RouterActivityStarter routerActivityStarter, 
								  UserSpecificPayloadGenerator userSpecificPayloadGenerator,
								  AdsRemovalPurchasedListener adsRemovalPurchasedListener,
								  UserSpecificPayloadValidator userSpecificPayloadValidator) {
		this.helper = helper;
		this.routerActivityStarter = routerActivityStarter;
		this.userSpecificPayloadGenerator = userSpecificPayloadGenerator;
		this.adsRemovalPurchasedListener = adsRemovalPurchasedListener;
		this.userSpecificPayloadValidator = userSpecificPayloadValidator;
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
		routerActivityStarter.startRouterActivity(RouteReason.FOR_IN_APP_BILLING, RouteDialog.NONE);
	}

	@Override
	public void onRouterActivityCreated(Activity activity) {
		this.routerActivity = activity;
		if(!purchaseUnderway){
			purchaseUnderway = true;
			helper.launchPurchaseFlow(activity, REMOVE_ADS_SKU, REQUEST_CODE, this, userSpecificPayloadGenerator.generateUserSpecificPayload());
		}
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		purchaseUnderway = false;
		if(result.isSuccess()){
			String payload = info.getDeveloperPayload();
			if(userSpecificPayloadValidator.payloadChecksOut(payload)){
				adsRemovalPurchasedListener.onAdsRemovalPurchased();	
			}
		}
		routerActivity.finish();
		routerActivity = null;
	}

}
