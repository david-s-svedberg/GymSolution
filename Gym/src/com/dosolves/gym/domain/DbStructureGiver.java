package com.dosolves.gym.domain;

public interface DbStructureGiver {

	String[] getAllColumns();
	String getTypeNamePlural();
	int getColumnIndex(String columnName);
	
	String getCreateSQL();

}
