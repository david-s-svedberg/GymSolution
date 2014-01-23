import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryStructureGiver;
import com.dosolves.gym.domain.category.CategoryUpdater;
import com.dosolves.gym.domain.category.CategoryUpdaterImpl;

@RunWith(RobolectricTestRunner.class)
public class CategoryUpdaterTest {

	private static final int CATEGORY_ID = 123;

	private static final String NEW_CATEGORY_NAME = "newCategoryName";

	private static final String CATEGORY_NAME = "CATEGORY_NAME";

	@Mock
	DataAccess dataAccessMock;
	
	CategoryUpdater sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);	
		sut = new CategoryUpdaterImpl(dataAccessMock);
	}
	
	@Test
	public void create_calls_dataAccess_with_correct_map(){		
		sut.create(NEW_CATEGORY_NAME);
		
		verify(dataAccessMock).create(eq(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL), argThat(new ArgumentMatcher<Map<String,Object>>(){
			
			@Override
			public boolean matches(Object arg0) {
				if(!(arg0 instanceof Map<?,?>) || arg0 == null)
					return false;
				@SuppressWarnings("unchecked")
				Map<String, Object> values = (Map<String, Object>) arg0;
				if (values.get(CategoryStructureGiver.NAME_PROPERTY_NAME).equals(NEW_CATEGORY_NAME))
					return true;				
				else
					return false;
			}			
		}));
	}
	@Test
	public void delete_calls_dataAccess_with_correct_idcolumnName(){
		Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
		
		sut.delete(category);
		
		verify(dataAccessMock).delete(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL,CategoryStructureGiver.ID_PROPERTY_NAME, CATEGORY_ID);
	}
}