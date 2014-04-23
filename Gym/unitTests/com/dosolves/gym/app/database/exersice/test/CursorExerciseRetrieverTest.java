package com.dosolves.gym.app.database.exersice.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.data.CursorExerciseFactory;
import com.dosolves.gym.domain.exercise.data.CursorExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;

@RunWith(RobolectricTestRunner.class)
public class CursorExerciseRetrieverTest {

	private static final String CATEGORY_ID_COLUMN_NAME = "CategoryId";
	private static final String EXERCISES = "Exercises";
	private static final int CATEGORY_ID = 123;
	
	@Mock
	DataAccess daoMock;
	@Mock
	GymCursor cursorMock;
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
		sut.getExercisesInCategory(CATEGORY_ID);		
		verify(daoMock).get(EXERCISES, CATEGORY_ID_COLUMN_NAME, CATEGORY_ID);		
	}
	
	@Test
	public void delegates_building_of_categories_to_factory(){
		when(daoMock.get(anyString(),anyString(), anyInt())).thenReturn(cursorMock);
		sut.getExercisesInCategory(CATEGORY_ID);
		verify(exerciseFactoryMock).CreateExercises(cursorMock);			
	}
	
}
