package com.dosolves.gym.domain.category;


public interface CategoryOptionMenuDialog {

	void show(Category categoryToShowOptionsFor, CategoryShouldBeDeletedCallback callback);

}
