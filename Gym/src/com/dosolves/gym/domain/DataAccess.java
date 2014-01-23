package com.dosolves.gym.domain;

import java.util.Map;

import android.database.Cursor;

public interface DataAccess {

	Cursor get(String type);

	void create(String string, Map<String, Object> keysAndvalues);
	void delete(String type, String typeIdPropertyName,	int id);

}
