package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.category.data.CascadingCategoryDeleter;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;

public class CascadingCategoryDeleterTest {
	
	private static final int ITEM_ID = 2456;
	
	@Mock
	ExerciseIdRetriever exerciseIdRetrieverMock;
	
	ItemDeleter sut;

	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CascadingCategoryDeleter(exerciseIdRetrieverMock);
	}
	
	@Test
	public void gets_all_ids_of_exercises(){
		sut.deleteItem(ITEM_ID);
		
		verify(exerciseIdRetrieverMock).getIds(ITEM_ID);
	}
	

}
