package com.dosolves.gym.domain.category.data;

import com.dosolves.gym.domain.category.Category;


public interface CategoryUpdater {

	void create(String newCategoryName);

	void delete(int categoryId);

	void rename(Category categoryToBeRenamed, String newCategoryName);

}
