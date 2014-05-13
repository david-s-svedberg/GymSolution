package com.dosolves.gym.app.database;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.data.DataAccess;

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
	public GymCursor get(String type) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String[] columns = dbStructureGivers.get(type).getAllColumns();
		
		return new SQLiteGymCursorAdapter(db.query(type, columns, null, null, null, null, null), db);		
	}
	
	@Override
	public GymCursor get(String type, String filterIdPropertyName, int filterId) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		
		String[] columns = dbStructureGivers.get(type).getAllColumns();
		String select = String.format("%s = ?",filterIdPropertyName);
		String[] selectArgs = new String[]{Integer.toString(filterId)};
		
		return new SQLiteGymCursorAdapter(db.query(type, columns, select, selectArgs, null, null, null), db);	
	}
	
	@Override
	public GymCursor getLast(String type, String filterIdPropertyName, int filterId, String datePropertyName) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		
		String[] columns = dbStructureGivers.get(type).getAllColumns();
		String select = String.format("%s = ?",filterIdPropertyName);
		String[] selectArgs = new String[]{Integer.toString(filterId)};
		
		return new SQLiteGymCursorAdapter(db.query(type, columns, select, selectArgs, null, null, String.format("%s DESC", datePropertyName),"1"), db);
	}

	@Override
	public int create(String type, Map<String, Object> keysAndvalues) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues contentValues = createContentValues(keysAndvalues);
		
		long newId = db.insert(type, null, contentValues);
		db.close();
		
		return (int)newId;
	}

	@Override
	public void delete(String type, String typeIdPropertyName, int id) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.delete(type, String.format("%s = ?", typeIdPropertyName), new String[]{Integer.toString(id)});
		db.close();
	}

	@Override
	public void update(String type, String typeIdPropertyName, int id, Map<String, Object> updateKeysAndValues) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues contentValues = createContentValues(updateKeysAndValues);
		db.update(type, contentValues, String.format("%s = ?", typeIdPropertyName), new String[]{Integer.toString(id)});
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
	public boolean exists(String type, String typeIdPropertyName, int id) {
		String rawSql = String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE %s=? LIMIT 1)", type, typeIdPropertyName);
		String[] args = new String[]{Integer.toString(id)};
		
		return queryIfexists(rawSql, args);		
	}
	
	@Override
	public boolean exists(String type) {
		String rawSql = String.format("SELECT EXISTS(SELECT 1 FROM %s LIMIT 1)", type);
		
		return queryIfexists(rawSql, new String[]{});
	}

	private boolean queryIfexists(String rawSql, String[] args) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(rawSql, args);
		cursor.moveToFirst();
		
		boolean exists = cursor.getInt(0) == 1;
		
		cursor.close();
		db.close();
		return exists;
	}

}
