package com.dosolves.gym.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.data.HighLevelSetIdRetriever;
import com.dosolves.gym.domain.performance.data.SetIdRetriever;
import com.dosolves.gym.domain.performance.data.SetRetriever;

public class SetIdRetrieverTest {

	private static final int SET_ID2 = 3465;

	private static final int SET_ID1 = 156;

	private static final int EXERCISE_ID = 46;

	@Mock
	SetRetriever setRetrieverMock;
	
	SetIdRetriever sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new HighLevelSetIdRetriever(setRetrieverMock);		
	}
	
	@Test
	public void polls_exercise_retriever_for_exercises(){
		sut.getIds(EXERCISE_ID);
		
		verify(setRetrieverMock).getSetsInExercise(EXERCISE_ID);
	}
	
	@Test
	public void gets_all_ids(){
		List<Set> sets = new ArrayList<Set>();
		sets.add(new Set(SET_ID1, EXERCISE_ID, 12,50.5, null));
		sets.add(new Set(SET_ID2, EXERCISE_ID, 12,50.5, null));
		
		
		when(setRetrieverMock.getSetsInExercise(EXERCISE_ID)).thenReturn(sets);
		
		int[] ids = sut.getIds(EXERCISE_ID);
		
		assertEquals(2, ids.length);
		assertTrue(contains(SET_ID1, ids));
		assertTrue(contains(SET_ID2, ids));
	}

	private boolean contains(int id, int[] ids) {
		for(int current : ids)
			if(current == id)
				return true;
		
		return false;
	}

	
}
