package com.dosolves.gym.app.database.test;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.robolectric.RobolectricTestRunner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;

import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.data.DataAccess;

@RunWith(RobolectricTestRunner.class)
public class SQLiteDataAccessTest extends AndroidTestCase {
	
	private static final int FILTER_ID = 3465;

	private static final String FILTER_ID_PROPERTY_NAME = "filterIdPropertyName";
	
	private static final String DATE_PROPERTY_NAME = "datePropertyName";

	private static final int ID = 234;

	private static final String ID_COLUMN_NAME = "IdColumnName";

	private static final String VALUE = "value";

	private static final String KEY = "key";

	private static final String TYPE_NAME = "asd";

	@Mock
	SQLiteOpenHelper openHelperMock;
	@Mock
	DbStructureGiver typeDbStructureGiverMock;
	@Mock
	SQLiteDatabase dbMock;
	@Mock
	Cursor cursorMock;

	String[] columns = new String[]{"columnA", "columnB"};
	
	DataAccess sut;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(typeDbStructureGiverMock.getTypeNamePlural()).thenReturn(TYPE_NAME);
		sut = new SQLiteDataAccess(openHelperMock,typeDbStructureGiverMock);		
	}
	
	@Test
	public void get_with_only_type_name_calls_select_with_all_columns_for_type() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(typeDbStructureGiverMock.getAllColumns()).thenReturn(columns);
		
		sut.get(TYPE_NAME);
		
		verify(dbMock).query(TYPE_NAME, columns, null, null, null, null, null);
	}
	
	@Test
	public void create_calls_db_insert_with_provided_args() {
		HashMap<String, Object> keysAndvalues = new HashMap<String,Object>();
		keysAndvalues.put(KEY, VALUE);
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.create(TYPE_NAME, keysAndvalues);
		
		verify(dbMock).insert(eq(TYPE_NAME), (String)isNull(), argThat(new ArgumentMatcher<ContentValues>(){

			@Override
			public boolean matches(Object argument) {
				ContentValues val = (ContentValues)argument;
				if (val.get(KEY).equals(VALUE))
					return true;
				else						
					return false;
			}
			
		}));		
	}
	
	@Test
	public void closes_db_after_create() {
		HashMap<String, Object> keysAndvalues = new HashMap<String,Object>();
		keysAndvalues.put(KEY, VALUE);
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.create(TYPE_NAME, keysAndvalues);
		
		verify(dbMock).close();
	}
	
	@Test
	public void delete_sends_correct_delete_querry_to_db() {
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.delete(TYPE_NAME, ID_COLUMN_NAME, ID);
		
		verify(dbMock).delete(eq(TYPE_NAME), eq(String.format("%s = ?", ID_COLUMN_NAME)), argThat(new ArgumentMatcher<String[]>(){

			@Override
			public boolean matches(Object argument) {
				String[] whereArgs = (String[]) argument;
				if(whereArgs.length == 1 && whereArgs[0].equals(Integer.toString(ID)))
					return true;
				else						
					return false;
			}
			
		}));
		
	}
	
	@Test
	public void closes_db_after_delete() {
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.delete(TYPE_NAME, ID_COLUMN_NAME, ID);
		
		verify(dbMock).close();
	}
	
	@Test
	public void get_with_id_filter_creates_correct_select_parameters() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(typeDbStructureGiverMock.getAllColumns()).thenReturn(columns);
		
		sut.get(TYPE_NAME,FILTER_ID_PROPERTY_NAME, FILTER_ID);
		
		verify(dbMock).query(eq(TYPE_NAME), eq(columns), eq(String.format("%s = ?", FILTER_ID_PROPERTY_NAME)), argThat(new ArgumentMatcher<String[]>(){

			@Override
			public boolean matches(Object argument) {
				return selectArgumentsContainsFilterId((String[])argument);
			}

			private boolean selectArgumentsContainsFilterId(String[] selectArguments) {
				for(String current: selectArguments)
					if(current.equals(Integer.toString(FILTER_ID)))
						return true;
				return false;
			}
			
		}), isNull(String.class), isNull(String.class), isNull(String.class));		
	}
	
	@Test
	public void getLast_with_id_filter_creates_correct_select_parameters() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(typeDbStructureGiverMock.getAllColumns()).thenReturn(columns);
		
		sut.getLast(TYPE_NAME, FILTER_ID_PROPERTY_NAME, FILTER_ID, DATE_PROPERTY_NAME);
		
		verify(dbMock).query(eq(TYPE_NAME), eq(columns), eq(String.format("%s = ?", FILTER_ID_PROPERTY_NAME)), argThat(new ArgumentMatcher<String[]>(){

			@Override
			public boolean matches(Object argument) {
				return selectArgumentsContainsFilterId((String[])argument);
			}

			private boolean selectArgumentsContainsFilterId(String[] selectArguments) {
				for(String current: selectArguments)
					if(current.equals(Integer.toString(FILTER_ID)))
						return true;
				return false;
			}
			
		}), isNull(String.class), isNull(String.class), eq(DATE_PROPERTY_NAME+" DESC"),eq("1"));		
	}
	
	@Test
	public void update_sends_correct_delete_querry_to_db() {
		HashMap<String, Object> keysAndvalues = new HashMap<String,Object>();
		keysAndvalues.put(KEY, VALUE);
		
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.update(TYPE_NAME, ID_COLUMN_NAME, ID, keysAndvalues);
		
		verify(dbMock).update(eq(TYPE_NAME), argThat(new ArgumentMatcher<ContentValues>(){

			@Override
			public boolean matches(Object argument) {
				ContentValues val = (ContentValues)argument;
				if (val.get(KEY).equals(VALUE))
					return true;
				else						
					return false;
			}
			
		}), eq(String.format("%s = ?", ID_COLUMN_NAME)), argThat(new ArgumentMatcher<String[]>(){

			@Override
			public boolean matches(Object argument) {
				String[] whereArgs = (String[]) argument;
				if(whereArgs.length == 1 && whereArgs[0].equals(Integer.toString(ID)))
					return true;
				else						
					return false;
			}
			
		}));
		
	}
	
	@Test
	public void closes_db_after_update() {
		HashMap<String, Object> keysAndvalues = new HashMap<String,Object>();
		keysAndvalues.put(KEY, VALUE);
		
		when(openHelperMock.getWritableDatabase()).thenReturn(dbMock);		
		
		sut.update(TYPE_NAME, ID_COLUMN_NAME, ID, keysAndvalues);
		
		verify(dbMock).close();
	}
	
	@Test
	public void exists_sends_correct_querry_to_db() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(dbMock.rawQuery(anyString(),any(String[].class))).thenReturn(cursorMock);
		when(cursorMock.getInt(anyInt())).thenReturn(0);
		
		sut.exists(TYPE_NAME,ID_COLUMN_NAME,ID);
		
		verify(dbMock).rawQuery(eq(String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE %s=? LIMIT 1)", TYPE_NAME,ID_COLUMN_NAME)), argThat(new ArgumentMatcher<String[]>(){

			@Override
			public boolean matches(Object argument) {
				String[] whereArgs = (String[]) argument;
				if(whereArgs.length == 1 && whereArgs[0].equals(Integer.toString(ID)))
					return true;
				else						
					return false;
			}
			
		}));
	}
	
	@Test
	public void exists_returns_true_if_qursor_contains_a_1() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(dbMock.rawQuery(anyString(),any(String[].class))).thenReturn(cursorMock);
		when(cursorMock.getInt(anyInt())).thenReturn(1);
		
		assertTrue(sut.exists(TYPE_NAME,ID_COLUMN_NAME,ID));
	}
	
	@Test
	public void exists_returns_false_if_qursor_contains_a_0() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(dbMock.rawQuery(anyString(),any(String[].class))).thenReturn(cursorMock);
		when(cursorMock.getInt(anyInt())).thenReturn(0);
		
		assertFalse(sut.exists(TYPE_NAME,ID_COLUMN_NAME,ID));
	}
	
	@Test
	public void exists_closes_db_and_cursor() {
		when(openHelperMock.getReadableDatabase()).thenReturn(dbMock);		
		when(dbMock.rawQuery(anyString(),any(String[].class))).thenReturn(cursorMock);
		when(cursorMock.getInt(anyInt())).thenReturn(0);
		
		sut.exists(TYPE_NAME,ID_COLUMN_NAME,ID);
		
		verify(cursorMock).close();
		verify(dbMock).close();
	}
	
}
