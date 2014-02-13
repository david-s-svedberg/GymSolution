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

import com.dosolves.gym.app.TypeMatchingModelComposer;
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
	
	
	ModelComposer sut;
	
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new TypeMatchingModelComposer(categorytModelFactoryMock, 
											exerciseModelFactoryMock,
											performanceModelFactoryMock);
	}
	
	@Test
	public void polls_factory_for_category_components(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categorytModelFactoryMock).createAdapter(categoriesActivityMock);
		verify(categorytModelFactoryMock).createController(categoriesActivityMock, categoryAdapterMock);		
	}

	private void stubCategoryAdapterAndControllerCreation() {
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
	}
	
	@Test
	public void sets_adapter_on_category_activity(){
		stubCategoryAdapterAndControllerCreation();
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setListAdapter(categoryAdapterMock);
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
	public void polls_factory_for_exercise_components(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exerciseModelFactoryMock).createAdapter(exercisesActivityMock);
		verify(exerciseModelFactoryMock).createController(exercisesActivityMock, exerciseAdapterMock, exercisesActivityMock);		
	}
	
	@Test
	public void sets_adapter_on_exerciseActivity(){
		stubExerciseAdapterAndControllerCreation();
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setListAdapter(exerciseAdapterMock);
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

	private void stubExerciseAdapterAndControllerCreation() {
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock,exercisesActivityMock)).thenReturn(exerciseControllerMock);
	}
	
	@Test
	public void polls_factory_for_exercisePerformance_components(){
		stubPerformanceAdapterAndControllerCreation();
		
		sut.compose(performanceActivityMock);
		
		verify(performanceModelFactoryMock).createAdapter(performanceActivityMock);
		verify(performanceModelFactoryMock).createController(performanceActivityMock, performanceAdapterMock, performanceActivityMock, performanceActivityMock);		
	}

	private void stubPerformanceAdapterAndControllerCreation() {
		when(performanceModelFactoryMock.createAdapter(performanceActivityMock)).thenReturn(performanceAdapterMock);
		when(performanceModelFactoryMock.createController(performanceActivityMock, performanceAdapterMock, performanceActivityMock, performanceActivityMock)).thenReturn(performanceControllerMock);
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
	
	
}
