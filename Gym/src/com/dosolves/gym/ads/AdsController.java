package com.dosolves.gym.ads;

import com.dosolves.gym.domain.AdsShouldBeDisplayedDecider;

public class AdsController implements SystemEventListener, UserGestureListener {

	private AdsShouldBeDisplayedDecider decider;
	private ViewSetter viewSetter;
	private MenuSetter menuSetter;
	private AdsInitializer adsInitializer;
	private AdsRemovalBuyer adsRemovalBuyer;

	public AdsController(AdsShouldBeDisplayedDecider decider, 
							 ViewSetter viewSetter, 
							 MenuSetter menuItemAdder, 
							 AdsInitializer adsInitializer, 
							 AdsRemovalBuyer adsRemovalBuyer) {
		this.decider = decider;
		this.viewSetter = viewSetter;
		this.menuSetter = menuItemAdder;
		this.adsInitializer = adsInitializer;
		this.adsRemovalBuyer = adsRemovalBuyer;
	}

	@Override
	public void onUIAboutToBeShown() {
		if(decider.adsShouldBeDisplayed()){
			viewSetter.setAdsView();
			adsInitializer.init();
		}
		else{
			viewSetter.setAdsFreeView();
		}
	}

	@Override
	public void onMenuShouldBeCreated() {
		if(decider.adsShouldBeDisplayed()){
			menuSetter.setAdsMenu();
		}
		else{
			menuSetter.setAdsFreeMenu();
		}
	}

	@Override
	public void onPurchaseAdsRemovalRequested() {
		adsRemovalBuyer.buyAdsRemoval();
	}

}
