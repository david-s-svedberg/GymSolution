package com.dosolves.gym.domain.category;


public interface CategoryUpdater {

	void create(String newCategoryName);

	void delete(Category categoryToBeDeleted);

}
