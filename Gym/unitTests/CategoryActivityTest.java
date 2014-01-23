import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.view.MenuItem;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.category.CategoryActivity;
import com.dosolves.gym.domain.ItemMenuRequestedCallback;
import com.dosolves.gym.domain.category.AddCategoryRequestedCallBack;

@RunWith(RobolectricTestRunner.class)
public class CategoryActivityTest {

	private static final int POSITION = 2345;
	@Mock
	MenuItem menuItemMock;
	@Mock
	AddCategoryRequestedCallBack addCategoryRequestedCallbackMock;
	@Mock
	ItemMenuRequestedCallback itemMenuRequestedCallbackMock;
	
	CategoryActivity sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CategoryActivity();
		sut.setAddCategoryRequestedCallBack(addCategoryRequestedCallbackMock);
		sut.setItemMenuRequestedCallback(itemMenuRequestedCallbackMock);
	}
	
	@Test
	public void calls_addCategoryRequestedCallback_when_add_category_menu_item_is_selected(){
		when(menuItemMock.getItemId()).thenReturn(R.id.add_category);
		sut.onOptionsItemSelected(menuItemMock);
		verify(addCategoryRequestedCallbackMock).onAddCategoryRequested();
	}
	
	@Test
	public void calls_itemMenuRequestedCallback_when_item_is_long_clicked(){
		sut.onItemLongClick(null, null, POSITION, 0);
		verify(itemMenuRequestedCallbackMock).onItemMenuRequested(POSITION);
	}
	
}
