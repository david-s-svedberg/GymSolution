package com.dosolves.gym.domain.category;


import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.UserAsker;

public interface CategoryModelFactory {

	ArrayAdapter<Category> createAdapter(Context context);
	CategoryController createController(Context context,ArrayAdapter<Category> adapter);
	UserAsker getUserAsker();
	UserAsker createUserAsker(Context context);

}
