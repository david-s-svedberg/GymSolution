package com.dosolves.gym.domain.data;

import java.util.Map;

import com.dosolves.gym.domain.GymCursor;

public interface DataAccess {

	GymCursor get(String type);
	GymCursor get(String type, String filterIdPropertyName, int filterId);
	GymCursor getLast(String type, String filterIdPropertyName, int filterId,String datePropertyName);
	
	int create(String type, Map<String, Object> keysAndvalues);
	
	void delete(String type, String typeIdPropertyName,	int id);
	void update(String type, String typeIdPropertyName, int id, Map<String, Object> updateKeysAndValues);
	
	boolean exists(String type, String typeIdPropertyName, int id);
	boolean exists(String type);
	

}
