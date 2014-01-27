package com.dosolves.gym.acceptancetest;

import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class CleanDbTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
	
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
		if (testsExecutedSoFar == countTestCases())
			deleteDb();		
	}
	
	private void setupDb() {
		SQLiteOpenHelperSingeltonHolder.useTestDb();
		SQLiteOpenHelperSingeltonHolder.setContext(getActivity());		
	}

	private void deleteDb() {
		getActivity().deleteDatabase(SQLiteOpenHelperSingeltonHolder.getDbHelper().getDatabaseName());		
	}
}
