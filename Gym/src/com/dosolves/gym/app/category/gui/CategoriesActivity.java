package com.dosolves.gym.app.category.gui;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.domain.category.Category;

public class CategoriesActivity extends UserUpdateableItemsActivity{
	
	private ArrayAdapter<Category> adapter;

	public void setCategoryAdapter(ArrayAdapter<Category> adapter) {
		super.setListAdapter(adapter);
		this.adapter = adapter;
	}

	@Override
	protected int getIdOfItemAtPosition(int position) {
		return adapter.getItem(position).getId();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Workaround Android Issue:68325
		getActionBar().setTitle(R.string.categories);
		super.onCreate(savedInstanceState);
	}
	
}