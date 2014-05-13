package com.dosolves.gym.app.database;

import com.dosolves.gym.domain.category.data.CategoryStructureGiver;
import com.dosolves.gym.domain.data.DataAccess;

public class DataBaseEmptyCheckerImpl implements DataBaseEmptyChecker {

	private DataAccess dataAccess;

	public DataBaseEmptyCheckerImpl(DataAccess dataAccess) {
		this.dataAccess = dataAccess;		
	}

	@Override
	public boolean isDbEmpty() {
		return !dataAccess.exists(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL);
	}

}
