package com.dosolves.gym.domain.category.data;

public interface CategoryUpdater extends CategoryCreator {

	void delete(int categoryId);
	void rename(int id, String newCategoryName);

}
