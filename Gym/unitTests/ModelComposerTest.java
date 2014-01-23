import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.test.AndroidTestCase;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.TypeMatchingModelComposer;
import com.dosolves.gym.app.gui.category.CategoryActivity;
import com.dosolves.gym.domain.ModelComposer;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;

@RunWith(RobolectricTestRunner.class)
public class ModelComposerTest extends AndroidTestCase{

	@Mock
	CategoryActivity categoryActivityMock;
	@Mock
	CategoryModelFactory categorytModelFactoryMock;
	@Mock
	ArrayAdapter<Category> adapterMock;
	@Mock
	CategoryController controllerMock;
	
	ModelComposer sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new TypeMatchingModelComposer(categorytModelFactoryMock);
	}
	
	@Test
	public void polls_factory_for_category_components(){
		when(categorytModelFactoryMock.createAdapter(categoryActivityMock)).thenReturn(adapterMock);
		when(categorytModelFactoryMock.createController(categoryActivityMock, adapterMock)).thenReturn(controllerMock);
		
		sut.compose(categoryActivityMock);
		
		verify(categorytModelFactoryMock).createAdapter(categoryActivityMock);
		verify(categorytModelFactoryMock).createController(categoryActivityMock, adapterMock);		
	}
	
	@Test
	public void sets_adapter_on_category_activity(){
		when(categorytModelFactoryMock.createAdapter(categoryActivityMock)).thenReturn(adapterMock);
		when(categorytModelFactoryMock.createController(categoryActivityMock, adapterMock)).thenReturn(controllerMock);
		
		sut.compose(categoryActivityMock);
		
		verify(categoryActivityMock).setListAdapter(adapterMock);
	}
	
	@Test
	public void sets_controller_as_AddCategoryRequestedCallBack_on_Activity(){
		when(categorytModelFactoryMock.createAdapter(categoryActivityMock)).thenReturn(adapterMock);
		when(categorytModelFactoryMock.createController(categoryActivityMock, adapterMock)).thenReturn(controllerMock);
		
		sut.compose(categoryActivityMock);
		
		verify(categoryActivityMock).setAddCategoryRequestedCallBack(controllerMock);
	}
	
	@Test
	public void sets_controller_as_ItemMenuRequestedCallback_on_Activity(){
		when(categorytModelFactoryMock.createAdapter(categoryActivityMock)).thenReturn(adapterMock);
		when(categorytModelFactoryMock.createController(categoryActivityMock, adapterMock)).thenReturn(controllerMock);
		
		sut.compose(categoryActivityMock);
		
		verify(categoryActivityMock).setItemMenuRequestedCallback(controllerMock);
	}
	
	@Test
	public void calls_init_on_controller(){
		when(categorytModelFactoryMock.createAdapter(categoryActivityMock)).thenReturn(adapterMock);
		when(categorytModelFactoryMock.createController(categoryActivityMock, adapterMock)).thenReturn(controllerMock);
		
		sut.compose(categoryActivityMock);
		
		verify(controllerMock).init();
	}
}
