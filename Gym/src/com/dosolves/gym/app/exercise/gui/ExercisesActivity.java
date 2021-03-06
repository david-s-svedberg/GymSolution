package com.dosolves.gym.app.exercise.gui;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;

public class ExercisesActivity extends UserUpdateableItemsActivity implements CurrentCategoryHolder {

	public static final String CATEGORY_BUNDLE_KEY = "CATEGORY_BUNDLE_KEY";
	
	private Category currentCategory;

	private ArrayAdapter<Exercise> adapter;

	public void setExerciseAdapter(ArrayAdapter<Exercise> adapter) {
		super.setListAdapter(adapter);
		this.adapter = adapter;
	}
	
	@Override
	public Integer getId(int position) {
		return adapter.getItem(position).getId();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCurrentCategory();
		setupBackNavigation();		
	}

	private void setCurrentCategory() {
		currentCategory = (Category)getIntent().getSerializableExtra(CATEGORY_BUNDLE_KEY);
		setCurrentCategoryNameAsTitle();
	}

	private void setCurrentCategoryNameAsTitle() {
		if (currentCategory != null)
			setTitle(currentCategory.getName());
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
