package com.dosolves.gym.domain.category.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.ContextProvider;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.category.CategoryModelFactoryImpl;

@RunWith(RobolectricTestRunner.class)
public class CategoryModelFactoryImplTest {

	@Mock
	ContextProvider contextProviderMock;
	@Mock
	Context contextMock;
	@Mock
	ArrayAdapter<Category> adapterMock;
	
	CategoryModelFactory sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CategoryModelFactoryImpl();
	}
	
	@Test
	public void creates_adapter_with_provided_context(){
		ArrayAdapter<Category> adapter = sut.createAdapter(contextMock);
		assertSame(adapter.getContext(), contextMock);
	}
	
	@Test
	public void can_create_controller(){
		assertNotNull(sut.createController(contextMock, adapterMock));		
	}
}
