package com.dosolves.gym.domain.category;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DataAccess;

public class CategoryUpdaterImpl implements CategoryUpdater {

	private DataAccess dataAccess;
	
	public CategoryUpdaterImpl(DataAccess dataAccess) {
		this.dataAccess = dataAccess;	
	}

	@Override
	public void create(String newCategoryName) {
		Map<String, Object> keysAndvalues = new HashMap<String, Object>();
		keysAndvalues.put(CategoryStructureGiver.NAME_PROPERTY_NAME, newCategoryName);
		dataAccess.create(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL, keysAndvalues);
	}

	@Override
	public void delete(Category categoryToBeDeleted) {
		dataAccess.delete(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL, CategoryStructureGiver.ID_PROPERTY_NAME, categoryToBeDeleted.getId());
	}

}
