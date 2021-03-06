package com.dosolves.gym.dbtest;

import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class DbStrucutreTest extends AndroidTestCase {

	static SQLiteDatabase db;
	static int testsExecutedSoFar = 0;
	static boolean isFirstRun = true;

	@Override
	protected void setUp() throws Exception {
		if(isFirstRun){
			deleteDb();
			createDb();
			isFirstRun = false;
		}		
	}

	@Override
	protected void tearDown() throws Exception {
		testsExecutedSoFar++;
		if (testsExecutedSoFar == totalNumberOfTestCases()){
			deleteDb();
			testsExecutedSoFar = 0;
			isFirstRun = true;
		}					
	}

	private int totalNumberOfTestCases() {
		return countTestCases()+1; //have to add one for testandroidtestcasesetupproperly added by AndroidTestCase
	}

	private void createDb() {
		SQLiteOpenHelperSingeltonHolder.useTestDb();
		SQLiteOpenHelperSingeltonHolder.setContext(getContext());
		db = SQLiteOpenHelperSingeltonHolder.getDbHelper().getReadableDatabase();
	}

	private void deleteDb() {
		getContext().deleteDatabase(SQLiteOpenHelperSingeltonHolder.getDbHelper().getDatabaseName());		
	}

	protected boolean columnExists(String tableName, String columnName) {
		if (tableName == null || columnName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
		Cursor cursor = db.rawQuery(String.format("PRAGMA table_info('%s')", tableName), null);
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    else{
	    	while(!cursor.isAfterLast()){
	    		if(cursor.getString(1).equals(columnName)){
	    			cursor.close();
	    			return true;
	    		}
	    		cursor.moveToNext();
	    	}
	    }
	    cursor.close();
	    
		return false;
	}

	protected boolean tableExists(String tableName) {
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}

}