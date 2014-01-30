package com.dosolves.gym.app.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dosolves.gym.domain.GymCursor;

public class SQLiteGymCursorAdapter implements GymCursor {

	private Cursor cursor;
	private SQLiteDatabase database;

	public SQLiteGymCursorAdapter(Cursor cursor, SQLiteDatabase database) {
		this.cursor = cursor;
		this.database = database;
	}

	@Override
	public boolean moveToFirst() {
	 	return cursor.moveToFirst();
	}

	@Override
	public boolean isAfterLast() {
		return cursor.isAfterLast();
	}

	@Override
	public boolean moveToNext() {
		return cursor.moveToNext();
	}

	@Override
	public int getInt(int index) {
		return cursor.getInt(index);
	}

	@Override
	public String getString(int index) {
		return cursor.getString(index);
	}
	
	@Override
	public long getLong(int index) {
		return cursor.getLong(index);
	}
	
	@Override
	public double getDouble(int index) {
		return cursor.getDouble(index);
	}

	@Override
	public void close() {
		cursor.close();
		database.close();
	}

}
