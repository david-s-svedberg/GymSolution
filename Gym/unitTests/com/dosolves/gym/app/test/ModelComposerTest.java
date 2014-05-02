package com.dosolves.gym.app.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;
import android.test.AndroidTestCase;
import android.widget.ArrayAdapter;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.TypeMatchingModelComposer;
import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.ads.AdsRemovalBuyerAdapter;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.gui.ContextualMenuHandlerForListItems;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.app.gui.performance.ContextualMenuHandlerForSets;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.UserResponseListener;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseControllerImpl;
import com.dosolves.gym.inappbilling.IabHelper;

@RunWith(RobolectricTestRunner.class)
public class ModelComposerTest extends AndroidTestCase{

	ModelComposer sut;
	
	@Mock
	CategoriesActivity categoriesActivityMock;
	@Mock
	CategoryModelFactory categoryModelFactoryMock;
	@Mock
	ArrayAdapter<Category> categoryAdapterMock;
	@Mock
	CategoryController categoryControllerMock;
	@Mock
	ExerciseModelFactory exerciseModelFactoryMock;
	@Mock
	ExercisesActivity exercisesActivityMock;
	@Mock
	ArrayAdapter<Exercise> exerciseAdapterMock;
	@Mock
	ExerciseController exerciseControllerMock;	
	@Mock
	PerformanceModelFactory performanceModelFactoryMock;
	@Mock
	PerformanceActivity performanceActivityMock;
	@Mock
	PerformanceAdapter performanceAdapterMock;
	@Mock
	PerformanceController performanceControllerMock;
	@Mock
	AdsModelFactory adsModelFactoryMock;
	@Mock
	AdsController adsControllerMock;
	@Mock
	RouterActivity routerActivityMock;
	@Mock
	AdsRemovalBuyerAdapter adsRemovalBuyerMock;
	@Mock
	IabHelper iabHelperMock;
	@Mock
	SetLastResultUseCaseControllerImpl setLastResultControllerMock;
	@Mock
	UserAskerImpl userAskerMock;
	@Mock
	UserResponseListener userResponseListenerMock;
	@Mock
	ContextualMenuHandlerForListItems contextMenuHandlerMock;
	@Mock
	CommonModelFactory commonModelFactory;
	@Mock
	ContextualMenuHandlerForSets setContextMenuHandlerMock;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new TypeMatchingModelComposer(categoryModelFactoryMock, 
											exerciseModelFactoryMock,
											performanceModelFactoryMock,
											adsModelFactoryMock,
											commonModelFactory);
	}
	
	@Test
	public void polls_factory_for_category_components(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoryModelFactoryMock).createAdapter(categoriesActivityMock);
		verify(categoryModelFactoryMock).createController(categoriesActivityMock, categoryAdapterMock);
		verify(adsModelFactoryMock).createController(categoriesActivityMock);
	}

	private void stubCategoryAdapterAndControllerCreation() {
		when(categoryModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categoryModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		when(adsModelFactoryMock.createController(categoriesActivityMock)).thenReturn(adsControllerMock);
		when(commonModelFactory.createContextualMenuHandler(categoriesActivityMock)).thenReturn(contextMenuHandlerMock);
	}
	
	@Test
	public void sets_adapter_on_category_activity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setCategoryAdapter(categoryAdapterMock);		
	}
	
	@Test
	public void sets_controller_as_AddItemRequestedCallBack_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setAddItemRequestedCallBack(categoryControllerMock);
	}
	
	@Test
	public void sets_contextMenuHandler_as_MultiChoiceModeListener_on_category_activity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setMultiChoiceModeListener(contextMenuHandlerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setOpenItemRequestedCallback(categoryControllerMock);
	}
	
	@Test
	public void adds_controller_as_system_event_listener_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).addSystemEventListener(categoryControllerMock);
	}
	
	@Test
	public void sets_adsController_as_AdsUserGestureListener_for_category(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setAdsUserGestureListener(adsControllerMock);
	}
	
	@Test
	public void sets_adsController_as_SystemEventListener_for_category(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).addSystemEventListener(adsControllerMock);
	}
	
	@Test
	public void sets_controller_as_userRequestListener_on_contextMenuHandler(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(contextMenuHandlerMock).addUserRequestListener(categoryControllerMock);
	}
	
	@Test
	public void polls_factory_for_exercise_components(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exerciseModelFactoryMock).createAdapter(exercisesActivityMock);
		verify(exerciseModelFactoryMock).createController(exercisesActivityMock, exerciseAdapterMock, exercisesActivityMock);
		verify(adsModelFactoryMock).createController(exercisesActivityMock);
	}
	
	@Test
	public void sets_adapter_on_exerciseActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setExerciseAdapter(exerciseAdapterMock);		
	}
	
	@Test
	public void sets_controller_as_AddItemRequestedCallBack_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setAddItemRequestedCallBack(exerciseControllerMock);
	}
	
	@Test
	public void sets_contextMenuHandler_as_MultiChoiceModeListener_on_exercise_activity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setMultiChoiceModeListener(contextMenuHandlerMock);
	}
	
	@Test
	public void sets_controller_as_userRequestListener_on_contextMenuHandler_exercise(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(contextMenuHandlerMock).addUserRequestListener(exerciseControllerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setOpenItemRequestedCallback(exerciseControllerMock);
	}
	
	@Test
	public void adds_controller_as_system_event_listener_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).addSystemEventListener(exerciseControllerMock);
	}
	
	@Test
	public void sets_adsController_as_SystemEventListener_for_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).addSystemEventListener(adsControllerMock);
	}
	
	@Test
	public void sets_adsController_as_AdsUserGestureListener_for_exercise(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setAdsUserGestureListener(adsControllerMock);
	}
	
	private void stubExerciseAdapterAndControllerCreation() {
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock,exercisesActivityMock)).thenReturn(exerciseControllerMock);
		when(adsModelFactoryMock.createController(exercisesActivityMock)).thenReturn(adsControllerMock);
		when(commonModelFactory.createContextualMenuHandler(exercisesActivityMock)).thenReturn(contextMenuHandlerMock);
	}
	
	@Test
	public void polls_factory_for_exercisePerformance_components(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceModelFactoryMock).createAdapter(performanceActivityMock);
		verify(performanceModelFactoryMock).createController(performanceActivityMock, performanceAdapterMock, performanceActivityMock, performanceActivityMock);
		verify(adsModelFactoryMock).createController(performanceActivityMock);
	}

	private void stubPerformanceAdapterAndControllerCreation() {
		when(performanceModelFactoryMock.createAdapter(performanceActivityMock)).thenReturn(performanceAdapterMock);
		when(performanceModelFactoryMock.createController(performanceActivityMock, performanceAdapterMock, performanceActivityMock, performanceActivityMock)).thenReturn(performanceControllerMock);
		when(performanceModelFactoryMock.createSetLastResultUseCaseController(performanceActivityMock)).thenReturn(setLastResultControllerMock);
		when(adsModelFactoryMock.createController(performanceActivityMock)).thenReturn(adsControllerMock);
		when(performanceModelFactoryMock.createContextHandler(performanceActivityMock)).thenReturn(setContextMenuHandlerMock);
	}
	
	@Test
	public void sets_adapter_on_activity(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setAdapter(performanceAdapterMock);				
	}
	
	@Test
	public void sets_controller_as_system_event_listener(){		
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).addSystemEventListener(performanceControllerMock);				
	}
	
	@Test
	public void sets_controller_as_NewSetShouldBeCreatedCallback(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setNewSetShouldBeCreatedCallback(performanceControllerMock);				
	}
	
	@Test
	public void sets_controller_as_SetShouldBeEditedCallback(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setSetShouldBeEditedCallback(performanceControllerMock);				
	}
	
	@Test
	public void sets_adsController_as_SystemEventListener_for_performance(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).addSystemEventListener(adsControllerMock);
	}
	
	@Test
	public void sets_adsController_as_AdsUserGestureListener_for_performance(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setAdsUserGestureListener(adsControllerMock);
	}
	
	@Test
	public void adds_SetLastResultUseCaseController_as_PerformanceSystemEventListener_for_performance(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).addSystemEventListener(setLastResultControllerMock);
	}
	
	@Test
	public void sets_context_menu_handler_on_adapter_for_further_use_when_creating_set_buttons(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceAdapterMock).setSetContextualMenuHandler(setContextMenuHandlerMock);
	}
	
	@Test
	public void adds_controller_as_user_request_listener_on_context_menu_handler_for_performance(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(setContextMenuHandlerMock).addUserRequestListener(performanceControllerMock);
	}
	
	@Test
	public void sets_AdsRemovalBuyer_as_RouterActivityCreatedListener_on_RouterActivity(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_IN_APP_BILLING);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.NONE);
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		
		when(adsModelFactoryMock.getAdsRemovalBuyer(routerActivityMock)).thenReturn(adsRemovalBuyerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setRouterActivityCreatedListener(adsRemovalBuyerMock);
		
	}
	
	@Test
	public void sets_iabHelper_as_ActivityResultListener_on_RouterActivity(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_IN_APP_BILLING);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.NONE);
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		
		when(adsModelFactoryMock.getIabHelper(routerActivityMock)).thenReturn(iabHelperMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setActivityResultListener(iabHelperMock);
		
	}
	
	@Test
	public void sets_category_UserAskerImpls_currentResponseListener_as_DialogResultListener_on_RouterActivity_for_delete_dialog(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_DELETE_DIALOG);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.CATEGORY);
		
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		when(categoryModelFactoryMock.getUserAsker()).thenReturn(userAskerMock);
		when(userAskerMock.getCurrentResponseListener()).thenReturn(userResponseListenerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setDialogResultListener(userResponseListenerMock);
		
	}
	@Test
	public void sets_exercise_UserAskerImpls_currentResponseListener_as_DialogResultListener_on_RouterActivity_for_delete_dialog(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_DELETE_DIALOG);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.EXERCISE);
		
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		when(exerciseModelFactoryMock.getUserAsker()).thenReturn(userAskerMock);
		when(userAskerMock.getCurrentResponseListener()).thenReturn(userResponseListenerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setDialogResultListener(userResponseListenerMock);
	}
	
	@Test
	public void sets_exercise_UserAskerImpls_as_RouterActivityCreatedListener(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_DELETE_DIALOG);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.EXERCISE);
		
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		when(exerciseModelFactoryMock.getUserAsker()).thenReturn(userAskerMock);
		when(userAskerMock.getCurrentResponseListener()).thenReturn(userResponseListenerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setRouterActivityCreatedListener(userAskerMock);
	}
	
	@Test
	public void sets_category_UserAskerImpls_as_RouterActivityCreatedListener(){
		Intent startIntent = new Intent();
		startIntent.putExtra(RouterActivity.REASON_KEY, RouteReason.FOR_DELETE_DIALOG);
		startIntent.putExtra(RouterActivity.MODULE_KEY, RouteModule.CATEGORY);
		
		when(routerActivityMock.getIntent()).thenReturn(startIntent);
		when(categoryModelFactoryMock.getUserAsker()).thenReturn(userAskerMock);
		when(userAskerMock.getCurrentResponseListener()).thenReturn(userResponseListenerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setRouterActivityCreatedListener(userAskerMock);
	}
	
}
