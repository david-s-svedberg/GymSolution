package com.dosolves.gym.app;

import com.dosolves.gym.app.category.CategoryModelFactoryImpl;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.ExerciseModelFactoryImpl;

import android.app.Application;
import android.content.Context;

public class GymApplication extends Application implements ContextSetter, ContextProvider{
	
	private ContextSetter contextSetter;
	private ActivityLifecycleCallbacks activityCreatedListener;
	private ContextProvider contextProvider;

	public GymApplication(){
		this.contextProvider = this;
		this.contextSetter = this;
		activityCreatedListener = new ActivityCreatedListener(new TypeMatchingModelComposer(new CategoryModelFactoryImpl(), new ExerciseModelFactoryImpl(), null));
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
