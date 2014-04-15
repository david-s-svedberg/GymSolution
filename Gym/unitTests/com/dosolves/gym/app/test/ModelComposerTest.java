package com.dosolves.gym.app.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.test.AndroidTestCase;
import android.widget.ArrayAdapter;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.app.TypeMatchingModelComposer;
import com.dosolves.gym.app.ads.AdsModelFactory;
import com.dosolves.gym.app.ads.AdsRemovalBuyerAdapter;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.ModelComposer;
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

	@Mock
	CategoriesActivity categoriesActivityMock;
	@Mock
	CategoryModelFactory categorytModelFactoryMock;
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
	
	ModelComposer sut;
	
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new TypeMatchingModelComposer(categorytModelFactoryMock, 
											exerciseModelFactoryMock,
											performanceModelFactoryMock,
											adsModelFactoryMock);
	}
	
	@Test
	public void polls_factory_for_category_components(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categorytModelFactoryMock).createAdapter(categoriesActivityMock);
		verify(categorytModelFactoryMock).createController(categoriesActivityMock, categoryAdapterMock);
		verify(adsModelFactoryMock).createController(categoriesActivityMock);
	}

	private void stubCategoryAdapterAndControllerCreation() {
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		when(adsModelFactoryMock.createController(categoriesActivityMock)).thenReturn(adsControllerMock);
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
	public void sets_controller_as_ItemMenuRequestedCallback_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setItemMenuRequestedCallback(categoryControllerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setOpenItemRequestedCallback(categoryControllerMock);
	}
	
	@Test
	public void sets_controller_as_ReadyToGetDataCallback_on_CategoriesActivity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setReadyToGetDataCallback(categoryControllerMock);
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
		
		verify(categoriesActivityMock).setSystemEventListener(adsControllerMock);
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
	public void sets_controller_as_ItemMenuRequestedCallback_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setItemMenuRequestedCallback(exerciseControllerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setOpenItemRequestedCallback(exerciseControllerMock);
	}
	
	@Test
	public void sets_controller_as_ReadyToGetDataCallback_on_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setReadyToGetDataCallback(exerciseControllerMock);
	}
	
	@Test
	public void sets_adsController_as_SystemEventListener_for_ExercisesActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setSystemEventListener(adsControllerMock);
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
	}
	
	@Test
	public void sets_adapter_on_activity(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setAdapter(performanceAdapterMock);				
	}
	
	@Test
	public void sets_controller_as_ReadyToGetDataCallback(){		
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceActivityMock).setReadyToGetDataCallback(performanceControllerMock);				
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
	public void sets_controller_as_SetCklickedCallback_on_adapter(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceAdapterMock).setSetMenuRequestedCallback(performanceControllerMock);				
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
	public void sets_AdsRemovalBuyer_as_RouterActivityCreatedListener_on_RouterActivity(){
	
		when(adsModelFactoryMock.getAdsRemovalBuyer(routerActivityMock)).thenReturn(adsRemovalBuyerMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setRouterActivityCreatedListener(adsRemovalBuyerMock);
		
	}
	
	@Test
	public void sets_iabHelper_as_ActivityResultListener_on_RouterActivity(){
		when(adsModelFactoryMock.getIabHelper(routerActivityMock)).thenReturn(iabHelperMock);
		
		sut.compose(routerActivityMock);
		
		verify(routerActivityMock).setActivityResultListener(iabHelperMock);
		
	}
	
}
