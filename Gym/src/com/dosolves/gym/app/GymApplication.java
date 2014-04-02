package com.dosolves.gym.app;

import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.category.CategoryModelFactoryImpl;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.ExerciseModelFactoryImpl;
import com.dosolves.gym.app.performance.PerformanceModelFactoryImpl;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;

import android.app.Application;
import android.content.Context;

public class GymApplication extends Application implements ContextSetter, ContextProvider{
	
	private ContextSetter contextSetter;
	private ActivityLifecycleCallbacks activityCreatedListener;
	private ContextProvider contextProvider;

	public GymApplication(){
		this.contextProvider = this;
		this.contextSetter = this;
		activityCreatedListener = new ActivityCreatedListener(createModelComposer());
	}

	private ModelComposer createModelComposer() {
		AdsModelFactory adsModelFactory = new AdsModelFactoryImpl();
		PerformanceModelFactory performanceModelFactory = new PerformanceModelFactoryImpl();
		ExerciseModelFactory exerciseModelFactory = new ExerciseModelFactoryImpl();
		CategoryModelFactory categoryModelFactory = new CategoryModelFactoryImpl();
		ModelComposer composer = new TypeMatchingModelComposer(categoryModelFactory, exerciseModelFactory, performanceModelFactory, adsModelFactory);
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
	}

	@Override
	public Context provideContext() {
		return getApplicationContext();
	}
	
}
