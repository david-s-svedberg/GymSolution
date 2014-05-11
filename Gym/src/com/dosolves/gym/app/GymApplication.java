package com.dosolves.gym.app;

import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.ads.AdsModelFactoryImpl;
import com.dosolves.gym.app.category.CategoryModelFactoryImpl;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.ExerciseModelFactoryImpl;
import com.dosolves.gym.app.performance.PerformanceModelFactoryImpl;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;
import com.dosolves.gym.utils.ResourcesUtils;

import android.app.Application;
import android.content.Context;

public class GymApplication extends Application implements ContextSetter, ContextProvider{
	
	private static final boolean TEST_MODE = true;
	
	private ContextSetter contextSetter;
	private ActivityLifecycleCallbacks activityCreatedListener;
	private ContextProvider contextProvider;

	public GymApplication(){
		this.contextProvider = this;
		this.contextSetter = this;
		activityCreatedListener = new ActivityCreatedListener(createModelComposer());
	}

	private ModelComposer createModelComposer() {
		AdsModelFactory adsModelFactory = new AdsModelFactoryImpl(TEST_MODE);
		CommonModelFactory commonModelFactory = new CommonModelFactoryImpl();
		PerformanceModelFactory performanceModelFactory = new PerformanceModelFactoryImpl(commonModelFactory);
		ExerciseModelFactory exerciseModelFactory = new ExerciseModelFactoryImpl(commonModelFactory);
		CategoryModelFactory categoryModelFactory = new CategoryModelFactoryImpl(commonModelFactory);
		
		ModelComposer composer = new TypeMatchingModelComposer(categoryModelFactory, 
															   exerciseModelFactory, 
															   performanceModelFactory, 
															   adsModelFactory, 
															   commonModelFactory);
		return composer;
	}
	
	public GymApplication(ContextSetter contextSetter, ActivityLifecycleCallbacks activityCreatedListener, ContextProvider contextProviderMock){
		this.contextSetter = contextSetter;
		this.activityCreatedListener = activityCreatedListener;
		this.contextProvider = contextProviderMock;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		contextSetter.setContext(contextProvider.provideContext());
		registerActivityLifecycleCallbacks(activityCreatedListener);
	}

	@Override
	public void setContext(Context context) {
		SQLiteOpenHelperSingeltonHolder.setContext(context);
		ResourcesUtils.setContext(context);
	}

	@Override
	public Context provideContext() {
		return getApplicationContext();
	}
	
}
