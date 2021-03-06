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

import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;
import com.dosolves.gym.domain.performance.data.CursorSetRetriever;
import com.dosolves.gym.domain.performance.data.SetRetriever;

@RunWith(RobolectricTestRunner.class)
public class CursorSetRetrieverTest {

	private static final String SET_ID_PROPERTY_NAME = "Id";
	private static final int SET_ID = 1358;
	private static final String DATE_PROPERTY_NAME = "Date";
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
	public void querries_dao_when_asked_for_sets(){		
		sut.getSetsInExercise(EXERCISE_ID);		
		verify(daoMock).get(SETS, EXERCISE_ID_PROPERTY_NAME, EXERCISE_ID);		
	}
	
	@Test
	public void delegates_building_of_sets_to_factory(){
		when(daoMock.get(anyString(),anyString(), anyInt())).thenReturn(cursorMock);
		sut.getSetsInExercise(EXERCISE_ID);
		verify(setFactoryMock).createSets(cursorMock);		
	}

	@Test
	public void querries_dao_when_asked_for_last_set(){		
		sut.getLastSetForExercise(exerciseMock);		
		verify(daoMock).getLast(SETS, EXERCISE_ID_PROPERTY_NAME, EXERCISE_ID, DATE_PROPERTY_NAME);		
	}
	
	@Test
	public void querries_dao_when_asked_for_set_by_id(){		
		sut.getSet(SET_ID);
		
		verify(daoMock).get(SETS, SET_ID_PROPERTY_NAME, SET_ID);		
	}
	
}
