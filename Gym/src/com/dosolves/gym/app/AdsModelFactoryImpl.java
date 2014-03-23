package com.dosolves.gym.app;

import android.app.Activity;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.ads.AdsInitializer;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.ads.ViewSetter;
import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.gui.AdsInitializerImpl;
import com.dosolves.gym.app.gui.CategoryAndExerciseViewSetter;
import com.dosolves.gym.app.gui.PerformaceViewSetter;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.AdsShouldBeDisplayedDecider;

public class AdsModelFactoryImpl implements AdsModelFactory {

	@Override
	public AdsController createController(Activity activity) {
		ViewSetter viewSetter = createViewSetter(activity);
		AdsInitializer adsInitializer = new AdsInitializerImpl(activity);
		MenuSetter menuSetter = (MenuSetter)activity;
		PreferenceRetriever preferenceRetriever = new ContextPreferenceRetriever(activity);
		AdsShouldBeDisplayedDecider adsShouldBeDisplayedDecider = new PreferenceAdsShouldBeDisplayedDecider(preferenceRetriever);
		
		return new AdsController(adsShouldBeDisplayedDecider, viewSetter, menuSetter, adsInitializer);		
	}

	private ViewSetter createViewSetter(Activity activity) {
		ViewSetter ret = null;
		
		if(activity instanceof UserUpdateableItemsActivity){
			ret = new CategoryAndExerciseViewSetter(activity);
		}
		else if(activity instanceof PerformanceActivity){
			ret = new PerformaceViewSetter(activity);
		}
		
		return ret;
	}

}
