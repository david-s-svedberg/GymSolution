package com.dosolves.gym.app.category.gui;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryOpener;

public class ContextCategoryOpener implements CategoryOpener{

	private Context context;

	public ContextCategoryOpener(Context context) {
		this.context = context;
	}

	@Override
	public void openCategory(Category category) {
		Intent intent = new Intent(context, ExercisesActivity.class);
		intent.putExtra(ExercisesActivity.CATEGORY_BUNDLE_KEY, category);
		context.startActivity(intent);
	}

}
