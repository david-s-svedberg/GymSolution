package com.dosolves.gym.app.database.exersice.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.database.Cursor;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.CursorExerciseFactory;
import com.dosolves.gym.domain.exercise.CursorExerciseRetriever;
import com.dosolves.gym.domain.exercise.ExerciseRetriever;

@RunWith(RobolectricTestRunner.class)
public class CursorExerciseRetrieverTest {

	private static final String CATEGORY_ID_COLUMN_NAME = "CategoryId";
	private static final String EXERCISES = "Exercises";
	private static final String CATEGORY_NAME = "categoryName";
	private static final int CATEGORY_ID = 123;
	@Mock
	DataAccess daoMock;
	@Mock
	Cursor cursorMock;
	@Mock
	CursorExerciseFactory exerciseFactoryMock;
	
	ExerciseRetriever sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CursorExerciseRetriever(daoMock, exerciseFactoryMock);
	}
	
	@Test
	public void querries_dao_when_asked_for_exercises(){
		sut.getExercisesInCategory(new Category(CATEGORY_ID, CATEGORY_NAME));		
		verify(daoMock).get(EXERCISES, CATEGORY_ID_COLUMN_NAME, CATEGORY_ID);		
	}
	
//	@Test
//	public void delegates_building_of_categories_to_factory(){
//		when(daoMock.get(anyString())).thenReturn(cursorMock);
//		sut.getCategories();
//		verify(categoryFactoryMock).CreateCategories(cursorMock);			
//	}
	
}
