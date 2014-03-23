package com.dosolves.gym.ads;

import com.dosolves.gym.domain.AdsShouldBeDisplayedDecider;

public class AdsController implements SystemEventListener {

	private AdsShouldBeDisplayedDecider decider;
	private ViewSetter viewSetter;
	private MenuSetter menuSetter;
	private AdsInitializer adsInitializer;

	public AdsController(AdsShouldBeDisplayedDecider decider, 
							 ViewSetter viewSetter, 
							 MenuSetter menuItemAdder, 
							 AdsInitializer adsInitializer) {
		this.decider = decider;
		this.viewSetter = viewSetter;
		this.menuSetter = menuItemAdder;
		this.adsInitializer = adsInitializer;
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

}
