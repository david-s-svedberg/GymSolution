package com.dosolves.gym.domain.category.data;

public interface CategoryUpdater {

	void create(String newCategoryName);
	void delete(int categoryId);
	void rename(int id, String newCategoryName);

}
