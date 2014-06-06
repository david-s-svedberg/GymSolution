package com.dosolves.gym.app.ads;

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
		skuToUse = TEST_SUCCESS_SKU;
	}
	
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		super.onIabPurchaseFinished(result, info);
		if(result.isSuccess()){
			helper.consumeAsync(info, null);
		}		
	}

}
