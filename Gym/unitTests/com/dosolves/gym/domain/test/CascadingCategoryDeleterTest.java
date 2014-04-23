package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.category.data.CascadingCategoryDeleter;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;

public class CascadingCategoryDeleterTest {
	
	private static final int EXERCISE_ID2 = 645;
	private static final int EXERCISE_ID1 = 2435;
	private static final int ITEM_ID = 2456;
	
	@Mock
	ExerciseIdRetriever exerciseIdRetrieverMock;
	@Mock
	ItemDeleter exerciseDeleterMock;
	@Mock
	CategoryUpdater categoryUpdaterMock;
	
	ItemDeleter sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CascadingCategoryDeleter(exerciseIdRetrieverMock, exerciseDeleterMock, categoryUpdaterMock);		
	}
	
	@Test
	public void gets_all_ids_of_exercises(){
		when(exerciseIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{});
		sut.deleteItem(ITEM_ID);
		
		verify(exerciseIdRetrieverMock).getIds(ITEM_ID);
	}
	
	@Test
	public void deletes_each_exercise(){
		when(exerciseIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{EXERCISE_ID1,EXERCISE_ID2});
		
		sut.deleteItem(ITEM_ID);
		
		verify(exerciseDeleterMock).deleteItem(EXERCISE_ID1);
		verify(exerciseDeleterMock).deleteItem(EXERCISE_ID2);
	}
	
	@Test
	public void deletes_category(){
		when(exerciseIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{});
		
		sut.deleteItem(ITEM_ID);
		
		verify(categoryUpdaterMock).delete(ITEM_ID);
	}
	
}
