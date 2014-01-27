package com.dosolves.gym.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;

public class RemoveProductionDb extends AndroidTestCase  {
	
	private void deleteDb() {
		getContext().deleteDatabase(SQLiteOpenHelperSingeltonHolder.getDbHelper().getDatabaseName());		
	}
	
	@SmallTest
	public void test(){
		deleteDb();
	}	
}
