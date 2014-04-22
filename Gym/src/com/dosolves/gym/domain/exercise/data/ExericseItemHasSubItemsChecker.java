package com.dosolves.gym.domain.exercise.data;

import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;
import com.dosolves.gym.domain.performance.data.SetStructureGiver;

public class ExericseItemHasSubItemsChecker implements ItemHasSubItemsChecker {

	private DataAccess dataAccess;

	public ExericseItemHasSubItemsChecker(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public boolean hasSubItems(int itemId) {
		return dataAccess.exists(SetStructureGiver.SET_TYPE_NAME_PLURAL, SetStructureGiver.EXERCISE_ID_PROPERTY_NAME, itemId);
	}

}
