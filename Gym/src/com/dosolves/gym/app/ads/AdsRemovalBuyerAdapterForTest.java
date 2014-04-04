package com.dosolves.gym.app.ads;

import android.app.Activity;

import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.AdsRemovalPurchasedListener;
import com.dosolves.gym.ads.UserSpecificPayloadValidator;
import com.dosolves.gym.inappbilling.IabHelper;
import com.dosolves.gym.inappbilling.IabHelper.OnIabPurchaseFinishedListener;
import com.dosolves.gym.inappbilling.IabResult;
import com.dosolves.gym.inappbilling.Purchase;

public class AdsRemovalBuyerAdapterForTest extends AdsRemovalBuyerAdapter  implements AdsRemovalBuyer, RouterActivityCreatedListener, OnIabPurchaseFinishedListener {
	
	private static final String TEST_SUCCESS_SKU = "android.test.purchased";

	public AdsRemovalBuyerAdapterForTest(IabHelper helper, 
								  		 RouterActivityStarter routerActivityStarter, 
								  		 UserSpecificPayloadGenerator userSpecificPayloadGenerator,
								  		 AdsRemovalPurchasedListener adsRemovalPurchasedListener,
								  		 UserSpecificPayloadValidator userSpecificPayloadValidator) {
		super(helper,routerActivityStarter,userSpecificPayloadGenerator,adsRemovalPurchasedListener,userSpecificPayloadValidator);
	}
	
//	@Override
//	public void onRouterActivityCreated(Activity activity) {
//		final Activity a = activity;
//		this.routerActivity = activity;
//		try {
//			helper.consumeAsync(new Purchase("inapp", "{\"packageName\":\"com.dosolves.gym\",\"orderId\":\"transactionId.android.test.purchased\",\"productId\":\"android.test.purchased\",\"developerPayload\":\"123\",\"purchaseTime\":0,\"purchaseState\":0,\"purchaseToken\":\"inapp:com.dosolves.gym:android.test.purchased\"}", ""), new OnConsumeFinishedListener() {
//				@Override
//				public void onConsumeFinished(Purchase purchase, IabResult result) {
//					if(result.isSuccess())
//						helper.launchPurchaseFlow(a, TEST_SUCCESS_SKU, REQUEST_CODE, AdsRemovalBuyerAdapterForTest.this, userSpecificPayloadGenerator.generateUserSpecificPayload());
//				}
//			});
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}				
//	}
	
	@Override
	public void onRouterActivityCreated(Activity activity) {
		this.routerActivity = activity;
		if(!purchaseUnderway){
			purchaseUnderway = true;
			helper.launchPurchaseFlow(activity, TEST_SUCCESS_SKU, REQUEST_CODE, AdsRemovalBuyerAdapterForTest.this, userSpecificPayloadGenerator.generateUserSpecificPayload());
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
			helper.consumeAsync(info, null);
		}
		routerActivity.finish();
		routerActivity = null;
	}

}
