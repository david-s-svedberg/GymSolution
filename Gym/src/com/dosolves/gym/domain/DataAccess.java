package com.dosolves.gym.domain;

import java.util.Map;

import android.database.Cursor;

public interface DataAccess {

	Cursor get(String type);
	Cursor get(String type, String filterIdPropertyName, int filterId);
	
	void create(String type, Map<String, Object> keysAndvalues);
	void delete(String type, String typeIdPropertyName,	int id);

	

}
