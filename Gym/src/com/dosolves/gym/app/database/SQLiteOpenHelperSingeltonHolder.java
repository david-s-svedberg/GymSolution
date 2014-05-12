package com.dosolves.gym.app.database;

import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.performance.database.SetDbStructureGiver;
import com.dosolves.gym.domain.DbStructureGiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenHelperSingeltonHolder {

	private SQLiteOpenHelperSingeltonHolder(){}
	
	private static Context context;
	
	private static String dbNameToUse = "GymDb";
	
	private static volatile SQLiteOpenHelper instance;	

	public static void setContext(Context context){
		SQLiteOpenHelperSingeltonHolder.context = context;
	}
	
	public static void useTestDb(){
		dbNameToUse = "TestGymDb";
	}
	
	public static SQLiteOpenHelper getDbHelper() {
		if (instance == null){
			synchronized (SQLiteOpenHelperSingeltonHolder.class) {
				if (instance == null){
					if (context == null)
						throw new IllegalStateException("Context was not provided");
					instance = new SQLiteOpenHelperSingeltonHolder().new GymSQLiteOpenHelper(new CategoryDbStructureGiver(), 
																							 new ExerciseDbStructureGiver(), 
																							 new SetDbStructureGiver());
				}
			}
		}
		return instance;
	}
	
	private class GymSQLiteOpenHelper extends SQLiteOpenHelper{

		private DbStructureGiver[] dbStructureGivers;

		public GymSQLiteOpenHelper(DbStructureGiver... dbStructureGivers) {
			super(context, dbNameToUse, null, 1);
			this.dbStructureGivers = dbStructureGivers;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			for(DbStructureGiver giver: dbStructureGivers){
				db.execSQL(giver.getCreateSQL());
			}			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
	
}
