package com.dosolves.gym.app.database.exersice.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.database.Cursor;

import com.dosolves.gym.app.database.exercise.CursorExerciseFactory;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.exercise.Exercise;

@RunWith(RobolectricTestRunner.class)
public class CursorExerciseFactoryTest {

	private static final int ID_COLUMN_INDEX = 0;
	private static final int NAME_COLUMN_INDEX = 1;
	private static final Integer CATEGORY_ID_COLUMN_INDEX = 2;
	
	private static final String EXERCISE_NAME = "EXERCISE_NAME";
	private static final int EXERCISE_ID = 1;
	private static final int CATEGORY_ID = 4;
	

	
	@Mock
	Cursor cursorMock;
	
	@Mock
	DbStructureGiver exerciseDbStructureGiverMock;
	
	CursorExerciseFactory sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		sut = new CursorExerciseFactory(exerciseDbStructureGiverMock);
	}

	@Test
	public void returns_empty_list_for_empty_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		assertEquals(0, sut.CreateExercises(cursorMock).size());
	}
	
	@Test
	public void builds_one_exercise_with_correct_data_from_one_row_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(exerciseDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(exerciseDbStructureGiverMock.getColumnIndex("CategoryId")).thenReturn(CATEGORY_ID_COLUMN_INDEX);
		when(exerciseDbStructureGiverMock.getColumnIndex("Name")).thenReturn(NAME_COLUMN_INDEX);
				
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(EXERCISE_ID);
		when(cursorMock.getInt(CATEGORY_ID_COLUMN_INDEX)).thenReturn(CATEGORY_ID);
		when(cursorMock.getString(NAME_COLUMN_INDEX)).thenReturn(EXERCISE_NAME);
		
		List<Exercise> exercises = sut.CreateExercises(cursorMock);
				
		assertEquals(1, exercises.size());
		Exercise exercise = exercises.get(0);
		assertEquals(EXERCISE_ID, exercise.getId());
		assertEquals(CATEGORY_ID, exercise.getCategoryId());
		assertEquals(EXERCISE_NAME, exercise.getName());
	}
	
	@Test
	public void builds_two_categories_from_two_rows_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false,false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(exerciseDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(exerciseDbStructureGiverMock.getColumnIndex("CategoryId")).thenReturn(CATEGORY_ID_COLUMN_INDEX);
		when(exerciseDbStructureGiverMock.getColumnIndex("Name")).thenReturn(NAME_COLUMN_INDEX);
				
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(EXERCISE_ID);
		when(cursorMock.getInt(CATEGORY_ID_COLUMN_INDEX)).thenReturn(CATEGORY_ID);
		when(cursorMock.getString(NAME_COLUMN_INDEX)).thenReturn(EXERCISE_NAME);
		
		assertEquals(2, sut.CreateExercises(cursorMock).size());
	}

	@Test
	public void closes_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		sut.CreateExercises(cursorMock);
		verify(cursorMock).close();
	}
	
}
