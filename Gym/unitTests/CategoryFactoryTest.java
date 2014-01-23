import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.database.Cursor;

import com.dosolves.gym.app.database.category.CursorCategoryFactory;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.category.Category;

@RunWith(RobolectricTestRunner.class)
public class CategoryFactoryTest {

	private static final int NAME_COLUMN_INDEX = 1;
	private static final int ID_COLUMN_INDEX = 0;
	private static final String CATEGORY_NAME = "CATEGORY_NAME";
	private static final int CATEGORY_ID = 1;
	
	@Mock
	Cursor cursorMock;
	
	@Mock
	DbStructureGiver categoryDbStructureGiverMock;
	
	CursorCategoryFactory sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		sut = new CursorCategoryFactory(categoryDbStructureGiverMock);
	}

	@Test
	public void returns_empty_list_for_empty_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		assertEquals(0, sut.CreateCategories(cursorMock).size());
	}
	
	@Test
	public void builds_one_category_with_correct_data_from_one_row_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(categoryDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(categoryDbStructureGiverMock.getColumnIndex("Name")).thenReturn(NAME_COLUMN_INDEX);
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(CATEGORY_ID);
		when(cursorMock.getString(NAME_COLUMN_INDEX)).thenReturn(CATEGORY_NAME);
		
		List<Category> categories = sut.CreateCategories(cursorMock);
				
		assertEquals(1, categories.size());
		assertEquals(CATEGORY_ID, categories.get(0).getId());	
		assertEquals(CATEGORY_NAME, categories.get(0).getName());
	}
	
	@Test
	public void builds_two_categories_from_two_rows_on_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(false,false, true);
		when(cursorMock.moveToNext()).thenReturn(true);
		
		when(categoryDbStructureGiverMock.getColumnIndex("Id")).thenReturn(ID_COLUMN_INDEX);
		when(categoryDbStructureGiverMock.getColumnIndex("Name")).thenReturn(NAME_COLUMN_INDEX);
		when(cursorMock.getInt(ID_COLUMN_INDEX)).thenReturn(CATEGORY_ID);
		when(cursorMock.getString(NAME_COLUMN_INDEX)).thenReturn(CATEGORY_NAME);
		
		assertEquals(2, sut.CreateCategories(cursorMock).size());
	}

	@Test
	public void closes_cursor(){
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.isAfterLast()).thenReturn(true);
		sut.CreateCategories(cursorMock);
		verify(cursorMock).close();
	}

	
}
