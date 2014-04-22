package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.category.data.CategoryItemHasSubItemsChecker;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;
import com.dosolves.gym.domain.exercise.data.ExericseItemHasSubItemsChecker;

public class ItemHasSubItemsCheckerTest {

	private static final int ITEM_ID = 45;

	@Mock
	DataAccess dataAccessMock;
	
	ItemHasSubItemsChecker sutCategory;
	ItemHasSubItemsChecker sutExercise;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sutCategory = new CategoryItemHasSubItemsChecker(dataAccessMock);
		sutExercise = new ExericseItemHasSubItemsChecker(dataAccessMock);
	}
	
	@Test
	public void categoryChecker_calls_exists_on_exercise_with_category_id(){
		sutCategory.hasSubItems(ITEM_ID);
		verify(dataAccessMock).exists("Exercises","CategoryId",ITEM_ID);
	}
	
	@Test
	public void exerciseChecker_calls_exists_on_sets_with_exercise_id(){
		sutExercise.hasSubItems(ITEM_ID);
		verify(dataAccessMock).exists("Sets","ExerciseId",ITEM_ID);
	}
}
