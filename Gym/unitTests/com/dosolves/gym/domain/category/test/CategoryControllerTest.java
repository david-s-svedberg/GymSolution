package com.dosolves.gym.domain.category.test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryOpener;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;

@RunWith(RobolectricTestRunner.class)
public class CategoryControllerTest {
	
	private static final String CATEGORY_NAME = "CATEGORY_NAME";
	private static final int CATEGORY_ID = 52;
	private static final int POSITION = 5345;
	private static final String NEW_CATEGORY_NAME = "NewCategoryName";
	
	@Mock
	ArrayAdapter<Category> adapterMock;
	@Mock
	CategoryRetriever retrieverMock;
	@Mock
	CreateItemDialogShower createItemDialogShowerMock;
	@Mock
	ItemOptionMenuDialogShower itemOptionMenuDialogShowerMock; 
	@Mock
	CategoryUpdater categoryUpdaterMock;
	@Mock
	CategoryOpener categoryOpenerMock;
	@Mock
	RenameDialogShower renameDialogShowerMock;
	
	private List<Category> categoriesMock;
	
	CategoryController sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		categoriesMock = new ArrayList<Category>();
		
		sut = new CategoryController(adapterMock, 
									 retrieverMock, 
									 createItemDialogShowerMock, 
									 categoryUpdaterMock, 
									 itemOptionMenuDialogShowerMock,
									 categoryOpenerMock,
									 renameDialogShowerMock);
	}
	
	@Test
    public void onReadyToGetData_updates_categories(){
            categoriesMock = new ArrayList<Category>();
            
            when(retrieverMock.getCategories()).thenReturn(categoriesMock);
            
            sut.onReadyToGetData();
            
            verifyCategoriesHaveBeenUpdated();
    }
	
	@Test
	public void calls_categoryUpdater_when_category_should_be_deleted(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		when(adapterMock.getItem(POSITION)).thenReturn(category);
		
		sut.onItemShouldBeDeleted(POSITION);
		verify(categoryUpdaterMock).delete(category);
	}
	
	@Test
	public void calls_categoryUpdater_when_category_should_be_renamed(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		when(adapterMock.getItem(POSITION)).thenReturn(category);
		
		sut.onItemShouldBeRenamed(POSITION, NEW_CATEGORY_NAME);
		verify(categoryUpdaterMock).rename(category, NEW_CATEGORY_NAME);
	}
	
	@Test
	public void calls_categoryUpdater_when_item_should_be_created(){
		sut.onItemShouldBeCreated(NEW_CATEGORY_NAME);
		
		verify(categoryUpdaterMock).create(NEW_CATEGORY_NAME);		
	}
	
	@Test
	public void doesnt_create_new_category_if_another_with_same_name_already_exists(){
		categoriesMock.add(new Category(CATEGORY_ID,NEW_CATEGORY_NAME));
		
		when(retrieverMock.getCategories()).thenReturn(categoriesMock);
		
		sut.onItemShouldBeCreated(NEW_CATEGORY_NAME);
		
		verifyZeroInteractions(categoryUpdaterMock);
	}
	
	@Test
	public void calls_categoryOpener_when_category_have_been_clicked(){
		Category categoryMock = new Category(CATEGORY_ID,NEW_CATEGORY_NAME);
		
		when(adapterMock.getItem(POSITION)).thenReturn(categoryMock);
		
		sut.onOpenItemRequested(POSITION);
		
		verify(categoryOpenerMock).openCategory(categoryMock);
	}

	private void verifyCategoriesHaveBeenUpdated() {
		verify(adapterMock).clear();
		verify(adapterMock).addAll(categoriesMock);
		verify(adapterMock).notifyDataSetChanged();
	}

}
