package com.dosolves.gym.ads;

import com.dosolves.gym.app.SystemEventListener;

public class AdsController implements SystemEventListener, AdsUserGestureListener {

	private AdsShouldBeDisplayedDecider decider;
	private ViewSetter viewSetter;
	private MenuSetter menuSetter;
	private AdViewStateHandler adsStateController;
	private AdsRemovalBuyer adsRemovalBuyer;

	public AdsController(AdsShouldBeDisplayedDecider decider, 
							 ViewSetter viewSetter, 
							 MenuSetter menuItemAdder, 
							 AdViewStateHandler adsInitializer, 
							 AdsRemovalBuyer adsRemovalBuyer) {
		this.decider = decider;
		this.viewSetter = viewSetter;
		this.menuSetter = menuItemAdder;
		this.adsStateController = adsInitializer;
		this.adsRemovalBuyer = adsRemovalBuyer;
	}

	@Override
	public void onUIAboutToBeCreated() {
		if(decider.adsShouldBeDisplayed()){
			viewSetter.setAdsView();
			adsStateController.init();
		}
		else{
			viewSetter.setAdsFreeView();
		}
	}
	
	@Override
	public void onUICreated() {}
	
	@Override
	public void onUIHidden() {
		if(decider.adsShouldBeDisplayed())
			adsStateController.pause();
	}
	
	@Override
	public void onUIInteractive() {
		if(decider.adsShouldBeDisplayed())
			adsStateController.resume();
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

	@Override
	public void onUIDestroyed() {
		adsStateController.destroy();
	}

}
