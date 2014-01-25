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
import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;

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
	
	ModelComposer sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new TypeMatchingModelComposer(categorytModelFactoryMock, exerciseModelFactoryMock);
	}
	
	@Test
	public void polls_factory_for_category_components(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categorytModelFactoryMock).createAdapter(categoriesActivityMock);
		verify(categorytModelFactoryMock).createController(categoriesActivityMock, categoryAdapterMock);		
	}
	
	@Test
	public void sets_adapter_on_category_activity(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setListAdapter(categoryAdapterMock);
	}
	
	@Test
	public void sets_controller_as_AddItemRequestedCallBack_on_CategoriesActivity(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setAddItemRequestedCallBack(categoryControllerMock);
	}
	
	@Test
	public void sets_controller_as_ItemMenuRequestedCallback_on_CategoriesActivity(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setItemMenuRequestedCallback(categoryControllerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_CategoriesActivity(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categoriesActivityMock).setOpenItemRequestedCallback(categoryControllerMock);
	}
	
	@Test
	public void calls_init_on_CategoryController(){
		when(categorytModelFactoryMock.createAdapter(categoriesActivityMock)).thenReturn(categoryAdapterMock);
		when(categorytModelFactoryMock.createController(categoriesActivityMock, categoryAdapterMock)).thenReturn(categoryControllerMock);
		
		sut.compose(categoriesActivityMock);
		
		verify(categoryControllerMock).init();
	}
	
	@Test
	public void polls_factory_for_exercise_components(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exerciseModelFactoryMock).createAdapter(exercisesActivityMock);
		verify(exerciseModelFactoryMock).createController(exercisesActivityMock, exerciseAdapterMock);		
	}
	
	@Test
	public void sets_adapter_on_exerciseActivity(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setListAdapter(exerciseAdapterMock);
	}
	
	@Test
	public void sets_controller_as_AddItemRequestedCallBack_on_ExercisesActivity(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setAddItemRequestedCallBack(exerciseControllerMock);
	}
	
	@Test
	public void sets_controller_as_ItemMenuRequestedCallback_on_ExercisesActivity(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setItemMenuRequestedCallback(exerciseControllerMock);
	}
	
	@Test
	public void sets_controller_as_OpenItemRequestedCallback_on_ExercisesActivity(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exercisesActivityMock).setOpenItemRequestedCallback(exerciseControllerMock);
	}
	
	@Test
	public void calls_init_on_ExercisesController(){
		when(exerciseModelFactoryMock.createAdapter(exercisesActivityMock)).thenReturn(exerciseAdapterMock);
		when(exerciseModelFactoryMock.createController(exercisesActivityMock, exerciseAdapterMock)).thenReturn(exerciseControllerMock);
		
		sut.compose(exercisesActivityMock);
		
		verify(exerciseControllerMock).init();
	}
	
}
