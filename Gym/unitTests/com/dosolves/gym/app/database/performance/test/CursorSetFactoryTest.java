package com.dosolves.gym.app.database.performance.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;

@RunWith(RobolectricTestRunner.class)
public class CursorSetFactoryTest {

	
	private static final int ID_COLUMN_INDEX = 0;
	private static final int EXERCISE_ID_COLUMN_INDEX = 1;
	private static final int REPS_ID_COLUMN_INDEX = 2;
	private static final int WEIGHT_ID_COLUMN_INDEX = 3;
	private static final int DATE_ID_COLUMN_INDEX = 4;
	
	private static final int EXERCISE_ID = 1;
	private static final int SET_ID = 1243;
	private static final int REPS = 12;
	private static final double WEIGHT = 50.5;
		
	@Mock
	GymCursor cursorMock;
	
	@Mock
	DbStructureGiver setDbStructureGiverMock;
	
	CursorSetFactory sut;
	private Date dateMock;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		dateMock = new Date();
		
		sut = new CursorSetFactory(setDbStructureGiverMock);
	}

	@Test
	public void returns_empty_list_for_empty_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		assertEquals(0, sut.CreateSets(cursorMock).size());
	}
	
	@Test
	public void builds_one_set_with_correct_data_from_one_row_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(setDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("ExerciseId")).thenReturn(EXERCISE_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Reps")).thenReturn(REPS_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Weight")).thenReturn(WEIGHT_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Date")).thenReturn(DATE_ID_COLUMN_INDEX);
				
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(SET_ID);
		when(cursorMock.getInt(EXERCISE_ID_COLUMN_INDEX)).thenReturn(EXERCISE_ID);
		when(cursorMock.getInt(REPS_ID_COLUMN_INDEX)).thenReturn(REPS);
		when(cursorMock.getDouble(WEIGHT_ID_COLUMN_INDEX)).thenReturn(WEIGHT);		
		when(cursorMock.getLong(DATE_ID_COLUMN_INDEX)).thenReturn(dateMock.getTime());
		
		List<Set> sets = sut.CreateSets(cursorMock);
				
		assertEquals(1, sets.size());
		Set set = sets.get(0);
		assertEquals(SET_ID, set.getId());
		assertEquals(EXERCISE_ID, set.getExerciseId());
		assertEquals(REPS, set.getReps());
		assertEquals(WEIGHT, set.getWeight(), 0.001);
		assertEquals(dateMock, set.getDate());
	}
	
	@Test
	public void builds_two_sets_from_two_rows_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false,false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(setDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("ExerciseId")).thenReturn(EXERCISE_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Reps")).thenReturn(REPS_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Weight")).thenReturn(WEIGHT_ID_COLUMN_INDEX);
		when(setDbStructureGiverMock.getColumnIndex("Date")).thenReturn(DATE_ID_COLUMN_INDEX);
				
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(SET_ID);
		when(cursorMock.getInt(EXERCISE_ID_COLUMN_INDEX)).thenReturn(EXERCISE_ID);
		when(cursorMock.getInt(REPS_ID_COLUMN_INDEX)).thenReturn(REPS);
		when(cursorMock.getDouble(WEIGHT_ID_COLUMN_INDEX)).thenReturn(WEIGHT);		
		when(cursorMock.getLong(DATE_ID_COLUMN_INDEX)).thenReturn(dateMock.getTime());
		
		assertEquals(2, sut.CreateSets(cursorMock).size());
	}

	@Test
	public void closes_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		sut.CreateSets(cursorMock);
		verify(cursorMock).close();
	}
	
}
