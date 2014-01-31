package com.dosolves.gym.app.gui.test;

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
import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.domain.ItemMenuRequestedCallback;
import com.dosolves.gym.domain.ReadyToGetDataCallback;

@RunWith(RobolectricTestRunner.class)
public class UserUpdateableItemsActivityTest {
	
	private static final int POSITION = 456;
	@Mock
	AddItemRequestedCallBack addItemRequestedCallBackMock;
	@Mock
	ItemMenuRequestedCallback itemMenuRequestedCallbackMock;
	@Mock
	OpenItemRequestedCallback openItemRequestedCallbackMock;
	@Mock
	ReadyToGetDataCallback readyToGetDataCallbackMock;
	@Mock
	MenuItem menuItemMock;
	
	UserUpdateableItemsActivity sut;	

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CategoriesActivity();
		sut.setAddItemRequestedCallBack(addItemRequestedCallBackMock);
		sut.setItemMenuRequestedCallback(itemMenuRequestedCallbackMock);
		sut.setOpenItemRequestedCallback(openItemRequestedCallbackMock);
		sut.setReadyToGetDataCallback(readyToGetDataCallbackMock);
	}
	
	@Test
	public void calls_addItemRequestedCallback_when_add_item_menu_item_is_selected(){
		when(menuItemMock.getItemId()).thenReturn(R.id.add_item);
		sut.onOptionsItemSelected(menuItemMock);
		verify(addItemRequestedCallBackMock).onAddItemRequested();
	}
	
	@Test
	public void calls_itemMenuRequestedCallback_when_item_is_long_clicked(){
		sut.onItemLongClick(null, null, POSITION, 0);
		verify(itemMenuRequestedCallbackMock).onItemMenuRequested(POSITION);
	}
	
	@Test
	public void calls_categoryClickedCallback_when_item_is_clicked(){
		sut.onListItemClick(null, null, POSITION, 0);
		verify(openItemRequestedCallbackMock).onOpenItemRequested(POSITION);
	}
	
	@Test
	public void calls_readyToGetData_when_onResume_is_called(){
		sut.onResume();
		verify(readyToGetDataCallbackMock).onReadyToGetData();
	}
	
}
