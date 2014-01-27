package com.dosolves.gym.domain.exercise.test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryOpener;
import com.dosolves.gym.domain.category.CategoryRetriever;
import com.dosolves.gym.domain.category.CategoryUpdater;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseOpener;
import com.dosolves.gym.domain.exercise.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.ExerciseUpdater;

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
