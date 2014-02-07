package com.dosolves.gym.acceptancetest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;

public abstract class CleanDbTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
	
	static int testsExecutedSoFar = 0;
	static boolean isFirstRun = true;
	
	public CleanDbTestCase(Class<T> clazz){
		super(clazz);
	}
	
	@Override
    protected void setUp() throws Exception {
		if(isFirstRun){
			setupDb();
			isFirstRun = false;
		}		
	}
	
	@Override
    protected void tearDown() throws Exception{
		testsExecutedSoFar++;
		if (testsExecutedSoFar == numberOfTestCases()){
			deleteDb();
			isFirstRun = true;
			testsExecutedSoFar = 0;
		}
	}

	protected abstract int numberOfTestCases();
	
	private void setupDb() {
		SQLiteOpenHelperSingeltonHolder.useTestDb();
		SQLiteOpenHelperSingeltonHolder.setContext(getInstrumentation().getTargetContext());
		deleteDb();
		setupDbHook();
	}

	protected void setupDbHook() {
		
	}

	private void deleteDb() {
		getInstrumentation().getTargetContext().deleteDatabase(SQLiteOpenHelperSingeltonHolder.getDbHelper().getDatabaseName());		
	}
}
