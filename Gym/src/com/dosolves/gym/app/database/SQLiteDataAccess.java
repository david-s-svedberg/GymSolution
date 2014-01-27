package com.dosolves.gym.app.database;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.DbStructureGiver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;

public class SQLiteDataAccess implements DataAccess {

	private SQLiteOpenHelper openHelper;
	private Map<String, DbStructureGiver> dbStructureGivers;

	public SQLiteDataAccess(SQLiteOpenHelper openHelper, DbStructureGiver... structureGivers) {
		this.openHelper = openHelper;
		this.dbStructureGivers = new HashMap<String, DbStructureGiver>();
		initDbStructureGivers(structureGivers);
	}

	private void initDbStructureGivers(DbStructureGiver[] structureGivers) {
		for(DbStructureGiver giver : structureGivers){
			dbStructureGivers.put(giver.getTypeNamePlural(), giver);
		}
	}

	@Override
	public Cursor get(String type) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String[] columns = dbStructureGivers.get(type).getAllColumns();
		Cursor cursor = db.query(type, columns, null, null, null, null, null);
		return cursor;		
	}

	@Override
	public void create(String type, Map<String, Object> keysAndvalues) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues contentValues = createContentValues(keysAndvalues);
		
		db.insert(type, null, contentValues);
		db.close();
	}

	private ContentValues createContentValues(Map<String, Object> keysAndvalues) {
		Parcel parcel = Parcel.obtain();
		parcel.writeMap(keysAndvalues);
		parcel.setDataPosition(0);
		ContentValues contentValues = ContentValues.CREATOR.createFromParcel(parcel);
		parcel.recycle();
		return contentValues;
	}

	@Override
	public void delete(String type, String typeIdPropertyName, int id) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.delete(type, String.format("%s = ?", typeIdPropertyName), new String[]{Integer.toString(id)});
		db.close();
	}

	@Override
	public Cursor get(String type, String filterIdPropertyName, int filterId) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		
		String[] columns = dbStructureGivers.get(type).getAllColumns();
		String select = String.format("%s = ?",filterIdPropertyName);
		String[] selectArgs = new String[]{Integer.toString(filterId)};
		Cursor cursor = db.query(type, columns, select, selectArgs, null, null, null);
		return cursor;	
	}

}
