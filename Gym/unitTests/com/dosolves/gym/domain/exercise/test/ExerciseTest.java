package com.dosolves.gym.domain.exercise.test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.exercise.Exercise;

@RunWith(RobolectricTestRunner.class)
public class ExerciseTest {
	
	private static final String EXERCISE_NAME = "exerciseName";
	private static final int EXERCISE_ID = 345;
	private static final int CATEGORY_ID = 52;
	Exercise sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		
		sut = new Exercise(EXERCISE_ID, CATEGORY_ID, EXERCISE_NAME);
		
	}
	
	@Test
    public void toString_returns_name(){
		assertEquals(EXERCISE_NAME, sut.toString());
	}

}
