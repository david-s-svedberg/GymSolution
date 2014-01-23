package com.dosolves.gym.app;

import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;

import android.app.Activity;
import android.widget.ArrayAdapter;

public class TypeMatchingModelComposer implements ModelComposer {

	private CategoryModelFactory categoryModelFactory;

	public TypeMatchingModelComposer(CategoryModelFactory categoryModelFactory) {
		this.categoryModelFactory = categoryModelFactory;
	}

	@Override
	public void compose(Activity activity) {
		if(activity instanceof CategoriesActivity){
			composeCategoryModel((CategoriesActivity)activity);
		}
	}

	private void composeCategoryModel(CategoriesActivity activity) {
		ArrayAdapter<Category> adapter = categoryModelFactory.createAdapter(activity);
		CategoryController controller = categoryModelFactory.createController(activity, adapter);
		activity.setListAdapter(adapter);
		activity.setAddCategoryRequestedCallBack(controller);
		activity.setItemMenuRequestedCallback(controller);
		activity.setCategoryClickedCallback(controller);
		
		controller.init();
	}

}
