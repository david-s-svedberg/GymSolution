package com.dosolves.gym.app.database.exersice.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.database.Cursor;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.GymCursor;
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
	GymCursor cursorMock;
	@Mock
	CursorExerciseFactory exerciseFactoryMock;
	
	private Category categoryMock;
	
	ExerciseRetriever sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	
		categoryMock = new Category(CATEGORY_ID, CATEGORY_NAME);
		
		sut = new CursorExerciseRetriever(daoMock, exerciseFactoryMock);
	}
	
	@Test
	public void querries_dao_when_asked_for_exercises(){		
		sut.getExercisesInCategory(categoryMock);		
		verify(daoMock).get(EXERCISES, CATEGORY_ID_COLUMN_NAME, CATEGORY_ID);		
	}
	
	@Test
	public void delegates_building_of_categories_to_factory(){
		when(daoMock.get(anyString(),anyString(), anyInt())).thenReturn(cursorMock);
		sut.getExercisesInCategory(categoryMock);
		verify(exerciseFactoryMock).CreateExercises(cursorMock);			
	}
	
}
