package com.dosolves.gym.app.database.category;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.category.CategoryStructureGiver;

public class CategoryDbStructureGiver implements DbStructureGiver{

	private Map<String, Integer> indexes;
	
	public CategoryDbStructureGiver(){
		indexes = new HashMap<String, Integer>();
		indexes.put(CategoryStructureGiver.ID_PROPERTY_NAME, 0);
		indexes.put(CategoryStructureGiver.NAME_PROPERTY_NAME, 1);
	}
	
	@Override
	public String[] getAllColumns() {
		return new String[]{CategoryStructureGiver.ID_PROPERTY_NAME, CategoryStructureGiver.NAME_PROPERTY_NAME};
	}

	@Override
	public String getTypeNamePlural() {
		return CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL;
	}

	@Override
	public int getColumnIndex(String columnName) {
		return indexes.get(columnName);
	}

	@Override
	public String getCreateSQL() {
		return String.format("CREATE TABLE %s ( ", CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL) +
			   String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT, ", CategoryStructureGiver.ID_PROPERTY_NAME) +
			   String.format("%s TEXT ", CategoryStructureGiver.NAME_PROPERTY_NAME) +
			   ")";
	}

}
