package com.dosolves.gym.domain.category;


import android.content.Context;
import android.widget.ArrayAdapter;

public interface CategoryModelFactory {

	ArrayAdapter<Category> createAdapter(Context context);
	CategoryController createController(Context context,ArrayAdapter<Category> adapter);

}
