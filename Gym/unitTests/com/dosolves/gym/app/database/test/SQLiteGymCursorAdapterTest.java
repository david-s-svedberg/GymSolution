package com.dosolves.gym.app.database.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dosolves.gym.app.database.SQLiteGymCursorAdapter;
import com.dosolves.gym.domain.GymCursor;

@RunWith(RobolectricTestRunner.class)
public class SQLiteGymCursorAdapterTest {

	private static final int STRING_INDEX = 467;
	private static final int INT_INDEX = 53;
	private static final int LONG_INDEX = 65;
	private static final int DOUBLE_INDEX = 65245;

	@Mock
	Cursor cursorMock;
	@Mock
	SQLiteDatabase dbMock;
	
	GymCursor sut;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		sut = new SQLiteGymCursorAdapter(cursorMock, dbMock);		
	}
	
	@Test
	public void passes_along_calls_to_adapted_methods() {
		
		sut.moveToFirst();
		sut.isAfterLast();
		sut.moveToNext();
		sut.getInt(INT_INDEX);
		sut.getString(STRING_INDEX);
		sut.getLong(LONG_INDEX);
		sut.getDouble(DOUBLE_INDEX);
		
		verify(cursorMock).moveToFirst();
		verify(cursorMock).isAfterLast();
		verify(cursorMock).moveToNext();
		verify(cursorMock).getInt(INT_INDEX);
		verify(cursorMock).getString(STRING_INDEX);
		verify(cursorMock).getLong(LONG_INDEX);
		verify(cursorMock).getDouble(DOUBLE_INDEX);
	}
	
	@Test
	public void close_closes_both_underlying_cursor_and_dn() {
		
		sut.close();
		
		verify(cursorMock).close();
		verify(dbMock).close();
		
	}

	
}
