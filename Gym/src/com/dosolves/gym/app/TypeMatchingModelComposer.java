package com.dosolves.gym.app;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;

public class TypeMatchingModelComposer implements ModelComposer {

	private CategoryModelFactory categoryModelFactory;
	private ExerciseModelFactory exerciseModelFactory;

	public TypeMatchingModelComposer(CategoryModelFactory categoryModelFactory, ExerciseModelFactory exerciseModelFactory) {
		this.categoryModelFactory = categoryModelFactory;
		this.exerciseModelFactory = exerciseModelFactory;
	}

	@Override
	public void compose(Activity activity) {
		if(activity instanceof CategoriesActivity){
			composeCategoryModel((CategoriesActivity)activity);
		}
		else if(activity instanceof ExercisesActivity){
			composeExerciseModel((ExercisesActivity)activity);
		}
		
	}

	private void composeExerciseModel(ExercisesActivity activity) {
		ArrayAdapter<Exercise> adapter = exerciseModelFactory.createAdapter(activity);
		ExerciseController controller = exerciseModelFactory.createController(activity, adapter);
		activity.setListAdapter(adapter);
	}

	private void composeCategoryModel(CategoriesActivity activity) {
		ArrayAdapter<Category> adapter = categoryModelFactory.createAdapter(activity);
		CategoryController controller = categoryModelFactory.createController(activity, adapter);
		activity.setListAdapter(adapter);
		activity.setAddItemRequestedCallBack(controller);
		activity.setItemMenuRequestedCallback(controller);
		activity.setOpenItemRequestedCallback(controller);
		
		controller.init();
	}

}
