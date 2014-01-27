package com.dosolves.gym.app.gui.exercise;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.category.Category;

public class ExercisesActivity extends UserUpdateableItemsActivity implements CurrentCategoryHolder {

	public static final String CATEGORY_BUNDLE_KEY = "CATEGORY_BUNDLE_KEY";
	
	private Category currentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupBackNavigation();
		currentCategory = (Category)savedInstanceState.get(CATEGORY_BUNDLE_KEY);
	}

	private void setupBackNavigation() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:			
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Category getCurrentCategory() {
		return currentCategory;
	}

}
