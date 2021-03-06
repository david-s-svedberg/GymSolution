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
import com.dosolves.gym.ads.AdsShouldBeDisplayedDecider;
import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;

@RunWith(RobolectricTestRunner.class)
public class UserUpdateableItemsActivityTest {
	
	private static final int POSITION = 456;

	UserUpdateableItemsActivity sut;
	
	@Mock
	AddItemRequestedCallBack addItemRequestedCallBackMock;
	@Mock
	OpenItemRequestedCallback openItemRequestedCallbackMock;
	@Mock
	MenuItem menuItemMock;
	@Mock
	AdsShouldBeDisplayedDecider adsShouldBeDisplayedCheckerMock;
	@Mock
	SystemEventListener systemEventListenerMock;
		

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new CategoriesActivity();
		sut.setAddItemRequestedCallBack(addItemRequestedCallBackMock);
		sut.setOpenItemRequestedCallback(openItemRequestedCallbackMock);
		sut.addSystemEventListener(systemEventListenerMock);
	}
	
	@Test
	public void calls_addItemRequestedCallback_when_add_item_menu_item_is_selected(){
		when(menuItemMock.getItemId()).thenReturn(R.id.add_item);
		sut.onOptionsItemSelected(menuItemMock);
		verify(addItemRequestedCallBackMock).onAddItemRequested();
	}
	
	@Test
	public void calls_openItemRequestedCallback_when_item_is_clicked(){
		sut.onListItemClick(null, null, POSITION, 0);
		verify(openItemRequestedCallbackMock).onOpenItemRequested(POSITION);
	}
	
	@Test
	public void calls_onUiInteractive_when_onResume_is_called(){
		sut.onResume();
		
		verify(systemEventListenerMock).onUIInteractive();
	}
	
}
