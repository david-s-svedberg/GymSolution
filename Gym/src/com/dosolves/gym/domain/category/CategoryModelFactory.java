package com.dosolves.gym.domain.category;


import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.gui.UserAskerImpl;

public interface CategoryModelFactory {

	ArrayAdapter<Category> createAdapter(Context context);
	CategoryController createController(Context context,ArrayAdapter<Category> adapter);
	UserAskerImpl getUserAsker();
	UserAskerImpl createUserAsker(Context context);

}
