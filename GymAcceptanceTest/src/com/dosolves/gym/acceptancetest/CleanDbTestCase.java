package com.dosolves.gym.acceptancetest;

import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public abstract class CleanDbTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
	
	static int testsExecutedSoFar = 0;
	static boolean isFirstRun = true;
	
	public CleanDbTestCase(Class<T> clazz){
		super(clazz);
	}
	
	@Override
    protected void setUp() throws Exception {
		Log.i("Hopp","basesetup");
		if(isFirstRun){
			setupDb();
			isFirstRun = false;
		}		
	}
	
	@Override
    protected void tearDown() throws Exception{
		testsExecutedSoFar++;
		Log.i("Hopp", String.format("%d av %d",testsExecutedSoFar, numberOfTestCases()));
		if (testsExecutedSoFar == numberOfTestCases()){
			deleteDb();
			isFirstRun = true;
			testsExecutedSoFar = 0;
		}
	}

	protected abstract int numberOfTestCases();
	
	private void setupDb() {
		Log.i("Hopp","setupDb");
		SQLiteOpenHelperSingeltonHolder.useTestDb();
		SQLiteOpenHelperSingeltonHolder.setContext(getInstrumentation().getTargetContext());
		setupDbHook();
	}

	protected void setupDbHook() {
		Log.i("Hopp","basesetupDbHook");
	}

	private void deleteDb() {
		getActivity().deleteDatabase(SQLiteOpenHelperSingeltonHolder.getDbHelper().getDatabaseName());
		Log.i("Hopp", "Db deleted");
	}
}
