package com.dosolves.gym.domain.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.ExercisePerformanceBuilder;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.set.Set;


@RunWith(RobolectricTestRunner.class)
public class ExercisePerformanceBuilderTest {

	ExercisePerformanceBuilder sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ExercisePerformanceBuilder();
	}
	
	@Test
    public void returns_empty_exercisePerformance_list_for_empty_set_list(){
        assertTrue(sut.build(new ArrayList<Set>()).isEmpty());         
    }
	
}
