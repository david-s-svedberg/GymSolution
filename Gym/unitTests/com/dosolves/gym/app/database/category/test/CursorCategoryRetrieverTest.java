package com.dosolves.gym.app.database.category.test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.dosolves.gym.app.database.category.CursorCategoryFactory;
import com.dosolves.gym.app.database.category.CursorCategoryRetriever;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.category.CategoryRetriever;

@RunWith(RobolectricTestRunner.class)
public class CursorCategoryRetrieverTest extends AndroidTestCase {

	private static final String CATEGORIES = "Categories";
	
	@Mock
	DataAccess daoMock;
	@Mock
	Cursor cursorMock;
	@Mock
	CursorCategoryFactory categoryFactoryMock;
	
	CategoryRetriever sut;


	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CursorCategoryRetriever(daoMock, categoryFactoryMock);
	}
	
	@Test
	public void querries_dao_when_asked_for_categories(){
		sut.getCategories();		
		verify(daoMock).get(CATEGORIES);		
	}
	
	@Test
	public void delegates_building_of_categories_to_factory(){
		when(daoMock.get(anyString())).thenReturn(cursorMock);
		sut.getCategories();
		verify(categoryFactoryMock).CreateCategories(cursorMock);			
	}
	
}


