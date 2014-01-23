package com.dosolves.gym.app.gui.category;

import android.content.Context;

import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryOpener;

public class ContextCategoryOpener implements CategoryOpener{

	private Context context;

	public ContextCategoryOpener(Context context) {
		this.context = context;
	}

	@Override
	public void openCategory(Category category) {
		
	}

}
