package com.dosolves.gym.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dosolves.gym.app.database.DataBaseEmptyChecker;

public class FirstTimeAppStartDeciderImpl implements FirstTimeAppStartDecider {

	private static final String FIRST_RUN_KEY = "firstRun";
	private PreferenceRetriever pereferenceRetriever;
	private DataBaseEmptyChecker dataBaseEmptyChecker;

	public FirstTimeAppStartDeciderImpl(PreferenceRetriever pereferenceRetriever, DataBaseEmptyChecker dataBaseEmptyChecker) {
		this.pereferenceRetriever = pereferenceRetriever;
		this.dataBaseEmptyChecker = dataBaseEmptyChecker;
	}

	@Override
	public boolean appIsRunningForTheFirstTime() {
		SharedPreferences preferences = pereferenceRetriever.getPreferences();
		boolean ret = preferences.getBoolean(FIRST_RUN_KEY, true);
		
		if(ret){
			ret = dataBaseEmptyChecker.isDbEmpty();
			Editor editor = preferences.edit();
			editor.putBoolean(FIRST_RUN_KEY, false);
			editor.commit();
		}	
		
		return ret;
	}

}
