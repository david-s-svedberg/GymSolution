package com.dosolves.gym.domain.category.data;

import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;
import com.dosolves.gym.domain.exercise.data.ExerciseStructureGiver;

public class CategoryItemHasSubItemsChecker implements ItemHasSubItemsChecker {

	private DataAccess dataAccess;

	public CategoryItemHasSubItemsChecker(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public boolean hasSubItems(int itemId) {
		return dataAccess.exists(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, itemId);
	}
}
