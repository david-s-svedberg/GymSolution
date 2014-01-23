import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryOptionMenuDialog;
import com.dosolves.gym.domain.category.CategoryRetriever;
import com.dosolves.gym.domain.category.CategoryUpdater;
import com.dosolves.gym.domain.category.CreateCategoryDialog;

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
	CreateCategoryDialog createCategoryDialogMock;
	@Mock
	CategoryOptionMenuDialog categoryOptionMenuDialogMock; 
	@Mock
	CategoryUpdater categoryUpdaterMock;
	
	private List<Category> categoriesMock;
	
	CategoryController sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		categoriesMock = new ArrayList<Category>();
		
		sut = new CategoryController(adapterMock, 
									 retrieverMock, 
									 createCategoryDialogMock, 
									 categoryUpdaterMock, 
									 categoryOptionMenuDialogMock);
	}
	
	@Test
	public void init_updates_categories(){
		categoriesMock = new ArrayList<Category>();
		
		when(retrieverMock.getCategories()).thenReturn(categoriesMock);
		
		sut.init();
		
		verifyCategoriesHaveBeenUpdated();
	}
	
	@Test
	public void shows_create_dialog_when_user_input_requests_it(){
		sut.onAddCategoryRequested();
		
		verify(createCategoryDialogMock).show(sut);		
	}
	
	@Test
	public void shows_menu_item_option_when_user_input_requests_it(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		when(adapterMock.getItem(POSITION)).thenReturn(category);
		
		sut.onItemMenuRequested(POSITION);
		
		verify(categoryOptionMenuDialogMock).show(category, sut);
	}
	
	@Test
	public void calls_categoryUpdater_when_category_should_be_deleted(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		sut.onCategoryShouldBeDeleted(category);
		verify(categoryUpdaterMock).delete(category);
	}
	
	@Test
	public void updates_categories_when_category_have_been_deleted(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		when(retrieverMock.getCategories()).thenReturn(categoriesMock);
		
		sut.onCategoryShouldBeDeleted(category);
		
		verifyCategoriesHaveBeenUpdated();
	}
	
	@Test
	public void calls_categoryUpdater_when_category_should_be_created(){
		sut.onCategoryShouldBeCreated(NEW_CATEGORY_NAME);
		
		verify(categoryUpdaterMock).create(NEW_CATEGORY_NAME);		
	}
	
	@Test
	public void updates_adapter_categories_after_new_category_has_been_created(){
		when(retrieverMock.getCategories()).thenReturn(categoriesMock);
		
		sut.onCategoryShouldBeCreated(NEW_CATEGORY_NAME);
		
		verifyCategoriesHaveBeenUpdated();
	}
	
	@Test
	public void doesnt_create_new_category_if_another_with_same_name_already_exists(){
		categoriesMock.add(new Category(CATEGORY_ID,NEW_CATEGORY_NAME));
		
		when(retrieverMock.getCategories()).thenReturn(categoriesMock);
		
		sut.onCategoryShouldBeCreated(NEW_CATEGORY_NAME);
		
		verifyZeroInteractions(categoryUpdaterMock);
	}

	private void verifyCategoriesHaveBeenUpdated() {
		verify(adapterMock).clear();
		verify(adapterMock).addAll(categoriesMock);
		verify(adapterMock).notifyDataSetChanged();
	}

}
