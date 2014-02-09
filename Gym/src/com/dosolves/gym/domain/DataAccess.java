package com.dosolves.gym.domain;

import java.util.Map;

public interface DataAccess {

	GymCursor get(String type);
	GymCursor get(String type, String filterIdPropertyName, int filterId);
	
	void create(String type, Map<String, Object> keysAndvalues);
	void delete(String type, String typeIdPropertyName,	int id);
	void update(String type, String typeIdPropertyName, int id, Map<String, Object> updateKeysAndValues);

}
