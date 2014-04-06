package com.dosolves.gym.app;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.UserUpdateableItemsController;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseControllerImpl;

public class TypeMatchingModelComposer implements ModelComposer {

	private CategoryModelFactory categoryModelFactory;
	private ExerciseModelFactory exerciseModelFactory;
	private PerformanceModelFactory performanceModelFactory;
	private AdsModelFactory adsModelFactory;

	public TypeMatchingModelComposer(CategoryModelFactory categoryModelFactory, 
									 ExerciseModelFactory exerciseModelFactory, 
									 PerformanceModelFactory performanceModelFactory, 
									 AdsModelFactory adsModelFactory) {
		this.categoryModelFactory = categoryModelFactory;
		this.exerciseModelFactory = exerciseModelFactory;
		this.performanceModelFactory = performanceModelFactory;
		this.adsModelFactory = adsModelFactory;
	}

	@Override
	public void compose(Activity activity) {
		if(activity instanceof CategoriesActivity){
			composeCategoryModel((CategoriesActivity)activity);
		}
		else if(activity instanceof ExercisesActivity){
			composeExerciseModel((ExercisesActivity)activity);
		}
		else if(activity instanceof PerformanceActivity){
			composePerformanceModel((PerformanceActivity)activity);
		}
		else if(activity instanceof RouterActivity){
			composeRouterModel((RouterActivity)activity);
		}
		
	}

	private void composeRouterModel(RouterActivity activity) {
		activity.setRouterActivityCreatedListener(adsModelFactory.getAdsRemovalBuyer(activity));
		activity.setActivityResultListener(adsModelFactory.getIabHelper(activity));
	}

	private void composeExerciseModel(ExercisesActivity activity) {
		ArrayAdapter<Exercise> adapter = exerciseModelFactory.createAdapter(activity);
		ExerciseController controller = exerciseModelFactory.createController(activity, adapter, activity);
		
		composeUserUpdateableItemsModel(activity, controller, adapter);
	}

	private void composeCategoryModel(CategoriesActivity activity) {
		ArrayAdapter<Category> adapter = categoryModelFactory.createAdapter(activity);
		CategoryController controller = categoryModelFactory.createController(activity, adapter);
		
		composeUserUpdateableItemsModel(activity, controller, adapter);		
	}
	
	private void composeUserUpdateableItemsModel(UserUpdateableItemsActivity activity, UserUpdateableItemsController controller, ListAdapter adapter) {
		AdsController adsController = adsModelFactory.createController(activity);
		activity.setSystemEventListener(adsController);
		activity.setAdsUserGestureListener(adsController);
		activity.setListAdapter(adapter);
		activity.setAddItemRequestedCallBack(controller);
		activity.setItemMenuRequestedCallback(controller);
		activity.setOpenItemRequestedCallback(controller);
		activity.setReadyToGetDataCallback(controller);		
	}
	
	private void composePerformanceModel(PerformanceActivity activity) {
		PerformanceAdapter adapter = performanceModelFactory.createAdapter(activity);	
		PerformanceController controller = performanceModelFactory.createController(activity, adapter, activity, activity);
		AdsController adsController = adsModelFactory.createController(activity);
		SetLastResultUseCaseControllerImpl setLastResultUCC = performanceModelFactory.createSetLastResultUseCaseController(activity);
		
		adapter.setSetMenuRequestedCallback(controller);
		activity.addSystemEventListener(adsController);
		activity.addSystemEventListener(setLastResultUCC);
		activity.setAdsUserGestureListener(adsController);
		activity.setAdapter(adapter);
		activity.setNewSetShouldBeCreatedCallback(controller);
		activity.setSetShouldBeEditedCallback(controller);
		activity.setReadyToGetDataCallback(controller);
	}

}