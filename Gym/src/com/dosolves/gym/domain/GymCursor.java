package com.dosolves.gym.domain;

public interface GymCursor {

	boolean moveToFirst();
	boolean isAfterLast();
	boolean moveToNext();
	int getInt(int index);
	String getString(int index);
	void close();
	long getLong(int index);
	double getDouble(int index);

}
