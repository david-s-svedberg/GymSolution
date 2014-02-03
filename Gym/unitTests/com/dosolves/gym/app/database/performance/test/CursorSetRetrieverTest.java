package com.dosolves.gym.app.database.performance.test;

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

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;
import com.dosolves.gym.domain.performance.data.CursorSetRetriever;
import com.dosolves.gym.domain.performance.data.SetRetriever;

@RunWith(RobolectricTestRunner.class)
public class CursorSetRetrieverTest {

	private static final String EXERCISE_ID_PROPERTY_NAME = "ExerciseId";
	private static final String SETS = "Sets";
	private static final int CATEGORY_ID = 2345;
	private static final int EXERCISE_ID = 123;
	private static final String EXERCISE_NAME = "exerciseName";
	
	@Mock
	DataAccess daoMock;
	@Mock
	GymCursor cursorMock;
	@Mock
	CursorSetFactory setFactoryMock;
	
	Exercise exerciseMock;
	
	SetRetriever sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		exerciseMock = new Exercise(EXERCISE_ID, CATEGORY_ID, EXERCISE_NAME);
		
		sut = new CursorSetRetriever(daoMock, setFactoryMock);
	}
	
	@Test
	public void querries_dao_when_asked_for_exercises(){		
		sut.getSetsInExercise(exerciseMock);		
		verify(daoMock).get(SETS, EXERCISE_ID_PROPERTY_NAME, EXERCISE_ID);		
	}
	
	@Test
	public void delegates_building_of_sets_to_factory(){
		when(daoMock.get(anyString(),anyString(), anyInt())).thenReturn(cursorMock);
		sut.getSetsInExercise(exerciseMock);
		verify(setFactoryMock).CreateSets(cursorMock);		
	}
	
}
