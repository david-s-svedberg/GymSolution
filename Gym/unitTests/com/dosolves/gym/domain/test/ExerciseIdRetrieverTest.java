package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.HighLevelExerciseIdRetriever;

public class ExerciseIdRetrieverTest {

	private static final int EXERCISE_ID2 = 45;

	private static final int EXERCISE_ID1 = 234;

	private static final int CATEGORY_ID = 2452;

	@Mock
	ExerciseRetriever exerciseRetrieverMock;
	
	ExerciseIdRetriever sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new HighLevelExerciseIdRetriever(exerciseRetrieverMock);		
	}
	
	@Test
	public void polls_exercise_retriever_for_exercises(){
		sut.getIds(CATEGORY_ID);
		
		verify(exerciseRetrieverMock).getExercisesInCategory(CATEGORY_ID);
	}
	
	@Test
	public void gets_all_ids(){
		List<Exercise> exercises = new ArrayList<Exercise>();
		exercises.add(new Exercise(EXERCISE_ID1, CATEGORY_ID, "sdfd"));
		exercises.add(new Exercise(EXERCISE_ID2, CATEGORY_ID, "sdfd"));
		
		when(exerciseRetrieverMock.getExercisesInCategory(CATEGORY_ID)).thenReturn(exercises);
		
		int[] ids = sut.getIds(CATEGORY_ID);
		
		assertEquals(2, ids.length);
		assertTrue(contains(EXERCISE_ID1, ids));
		assertTrue(contains(EXERCISE_ID2, ids));
	}

	private boolean contains(int id, int[] ids) {
		for(int current : ids)
			if(current == id)
				return true;
		
		return false;
	}
	
	
}
