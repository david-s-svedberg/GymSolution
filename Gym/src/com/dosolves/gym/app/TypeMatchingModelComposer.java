package com.dosolves.gym.app;

import android.app.Activity;
import android.content.Intent;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.app.performance.gui.ContextualMenuHandlerForSets;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.UserAsker;
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
	private CommonModelFactory commonModelFactory;

	public TypeMatchingModelComposer(CategoryModelFactory categoryModelFactory, 
									 ExerciseModelFactory exerciseModelFactory, 
									 PerformanceModelFactory performanceModelFactory, 
									 AdsModelFactory adsModelFactory, 
									 CommonModelFactory commonModelFactory) {
		this.categoryModelFactory = categoryModelFactory;
		this.exerciseModelFactory = exerciseModelFactory;
		this.performanceModelFactory = performanceModelFactory;
		this.adsModelFactory = adsModelFactory;
		this.commonModelFactory = commonModelFactory;
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
		Intent startIntent = activity.getIntent();
		
		switch(getRoutReason(startIntent)){
			case FOR_DIALOG:
				composeDialogRouting(activity, startIntent);	
				break;
			case FOR_IN_APP_BILLING:
				composeInAppBillingRouting(activity);
				break;
			default:
				throw new IllegalArgumentException();
		}
		
	}

	private void composeInAppBillingRouting(RouterActivity activity) {
		activity.setRouterActivityCreatedListener(adsModelFactory.getAdsRemovalBuyer(activity));
		activity.setActivityResultListener(adsModelFactory.getIabHelper(activity));
	}

	private void composeDialogRouting(RouterActivity activity, Intent startIntent) {
		UserAsker userAsker = null;
		
		switch(getRouteDialog(startIntent)){
			case DELETE_CATEGORY:
				userAsker = categoryModelFactory.getUserAsker();
				break;
			case DELETE_EXERCISE:
				userAsker = exerciseModelFactory.getUserAsker();
				break;
			case ADD_DEFAULT_EXERCISES:
				userAsker = commonModelFactory.getUserAskerForAddDefaultExercise();
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		activity.setDialogResultListener(userAsker.getCurrentResponseListener());
		activity.setRouterActivityCreatedListener(userAsker);
	}

	private RouteDialog getRouteDialog(Intent startIntent) {
		return (RouteDialog)startIntent.getSerializableExtra(RouterActivity.DIALOG_KEY);
	}

	private RouteReason getRoutReason(Intent startIntent) {
		return (RouteReason)startIntent.getSerializableExtra(RouterActivity.REASON_KEY);
	}

	private void composeExerciseModel(ExercisesActivity activity) {
		ArrayAdapter<Exercise> adapter = exerciseModelFactory.createAdapter(activity);
		ExerciseController controller = exerciseModelFactory.createController(activity, adapter, activity);
		
		activity.setExerciseAdapter(adapter);
		
		composeUserUpdateableItemsModel(activity, controller, adapter);
	}

	private void composeCategoryModel(CategoriesActivity activity) {
		ArrayAdapter<Category> adapter = categoryModelFactory.createAdapter(activity);
		CategoryController controller = categoryModelFactory.createController(activity, adapter);
		
		activity.setCategoryAdapter(adapter);
		
		composeUserUpdateableItemsModel(activity, controller, adapter);		
	}
	
	private void composeUserUpdateableItemsModel(UserUpdateableItemsActivity activity, UserUpdateableItemsController controller, ListAdapter adapter) {
		AdsController adsController = adsModelFactory.createController(activity);
		
		ContextualMenuHandler contextualMenuHandler = commonModelFactory.createContextualMenuHandler(activity);
		contextualMenuHandler.addUserRequestListener(controller);
		
		activity.addSystemEventListener(adsController);
		activity.addSystemEventListener(controller);
		activity.setAdsUserGestureListener(adsController);		
		activity.setAddItemRequestedCallBack(controller);
		activity.setMultiChoiceModeListener((MultiChoiceModeListener)contextualMenuHandler);
		activity.setOpenItemRequestedCallback(controller);
	}
	
	private void composePerformanceModel(PerformanceActivity activity) {
		PerformanceAdapter adapter = performanceModelFactory.createAdapter(activity);
		ContextualMenuHandlerForSets contextHandler = performanceModelFactory.createContextHandler(activity);
		PerformanceController controller = performanceModelFactory.createController(activity, adapter, activity, activity);
		AdsController adsController = adsModelFactory.createController(activity);
		SetLastResultUseCaseControllerImpl setLastResultUCC = performanceModelFactory.createSetLastResultUseCaseController(activity);
		
		contextHandler.addUserRequestListener(controller);
		adapter.setSetContextualMenuHandler(contextHandler);
		
		activity.addSystemEventListener(adsController);
		activity.addSystemEventListener(setLastResultUCC);
		activity.addSystemEventListener(controller);
		activity.setAdsUserGestureListener(adsController);
		activity.setAdapter(adapter);
		activity.addUserGestureListener(controller);
		activity.addUserGestureListener(contextHandler.getUserGestureListener());
	}

}