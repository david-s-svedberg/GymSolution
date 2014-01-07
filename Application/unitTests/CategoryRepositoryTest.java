
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import android.test.AndroidTestCase;

import com.dosolves.gym.CategoryRepository;
import com.dosolves.gym.CursorCategoryRepository;
import com.dosolves.gym.DataAccess;

@RunWith(RobolectricTestRunner.class)
public class CategoryRepositoryTest extends AndroidTestCase {

	@Mock
	DataAccess daoMock;
	
	CategoryRepository sut;


	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		sut = new CursorCategoryRepository(daoMock);
	}
	
	@Test
	public void querries_dao_when_asked_for_categories(){
		when(daoMock.get(anyString())).thenReturn(null);
		
		sut.getCategories();
		
		verify(daoMock).get("Categories");
		verifyNoMoreInteractions(daoMock);
	}
	
	
}
