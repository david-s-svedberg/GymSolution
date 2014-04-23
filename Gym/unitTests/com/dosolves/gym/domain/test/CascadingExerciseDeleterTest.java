package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.exercise.data.CascadingExerciseDeleter;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.performance.data.SetIdRetriever;

public class CascadingExerciseDeleterTest {

	private static final int SET_ID2 = 645;
	private static final int SET_ID1 = 2435;
	private static final int ITEM_ID = 2456;
	
	@Mock
	SetIdRetriever setIdRetrieverMock;
	@Mock
	ItemDeleter setDeleterMock;
	@Mock
	ExerciseUpdater exerciseUpdaterMock;
	
	ItemDeleter sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CascadingExerciseDeleter(setIdRetrieverMock,setDeleterMock, exerciseUpdaterMock);
	}
	
	@Test
	public void gets_all_ids_of_sets(){
		when(setIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{});
		sut.deleteItem(ITEM_ID);
		
		verify(setIdRetrieverMock).getIds(ITEM_ID);
	}
	
	@Test
	public void deletes_each_exercise(){
		when(setIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{SET_ID1,SET_ID2});
		
		sut.deleteItem(ITEM_ID);
		
		verify(setDeleterMock).deleteItem(SET_ID1);
		verify(setDeleterMock).deleteItem(SET_ID2);
	}

	@Test
	public void deletes_exercise(){
		when(setIdRetrieverMock.getIds(ITEM_ID)).thenReturn(new int[]{});
		
		sut.deleteItem(ITEM_ID);
		
		verify(exerciseUpdaterMock).delete(ITEM_ID);
	}
	
}
