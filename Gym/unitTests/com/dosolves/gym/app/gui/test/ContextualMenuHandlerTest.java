package com.dosolves.gym.app.gui.test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.gui.ContextualMenuHandlerImpl;
import com.dosolves.gym.app.gui.PositionToIdTranslator;
import com.dosolves.gym.domain.UserRequestListener;

public class ContextualMenuHandlerTest {
	
	private static final int ITEM_ID2 = 2;
	private static final int ITEM_ID1 = 1;
	
	ContextualMenuHandler sut;
	MultiChoiceModeListener sutAsMultiChoiceModeListener;

	@Mock
	Menu menuMock;
	@Mock
	MenuItem renameItemMock;
	@Mock
	PositionToIdTranslator positionTranslatorMock;
	@Mock
	ActionMode actionModeMock;
	@Mock
	MenuInflater menuInflaterMock;
	@Mock
	MenuItem deleteItemMock;
	@Mock
	UserRequestListener userRequestListenerMock;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		ContextualMenuHandlerImpl sutImpl = new ContextualMenuHandlerImpl(positionTranslatorMock);
		sutImpl.addUserRequestListener(userRequestListenerMock);
		sut = sutImpl;
		sutAsMultiChoiceModeListener = sutImpl;
	}
	
	@Test
	public void disables_rename_menu_item_if_more_then_one_item_is_selected(){
		when(menuMock.getItem(R.id.rename_menu_item)).thenReturn(renameItemMock);
		when(positionTranslatorMock.getId(1)).thenReturn(1);
		when(positionTranslatorMock.getId(2)).thenReturn(2);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 2, 2, true);
		
		verify(renameItemMock).setEnabled(false);
	}
	
	@Test
	public void enables_rename_menu_item_when_one_item_is_selected(){
		when(menuMock.getItem(R.id.rename_menu_item)).thenReturn(renameItemMock);
		when(positionTranslatorMock.getId(1)).thenReturn(1);
		when(positionTranslatorMock.getId(2)).thenReturn(2);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 2, 2, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 2, 2, false);
		
		verify(renameItemMock,times(2)).setEnabled(true);
		verify(renameItemMock).setEnabled(false);
	}
	
	@Test
	public void calls_user_request_listener_with_selected_ids_for_delete(){
		when(menuMock.getItem(R.id.rename_menu_item)).thenReturn(renameItemMock);
		
		when(positionTranslatorMock.getId(1)).thenReturn(ITEM_ID1);
		when(positionTranslatorMock.getId(2)).thenReturn(ITEM_ID2);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		when(deleteItemMock.getItemId()).thenReturn(R.id.delete_menu_item);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 2, 2, true);
		
		sutAsMultiChoiceModeListener.onActionItemClicked(actionModeMock, deleteItemMock);
		
		verify(userRequestListenerMock).deleteItems(argThat(new ArgumentMatcher<List<Integer>>() {

			@Override
			public boolean matches(Object argument) {
				@SuppressWarnings("unchecked")
				List<Integer> ids = (List<Integer>) argument;
				return ids.size() == 2 && ids.contains(ITEM_ID1) && ids.contains(ITEM_ID2);
			}
		}));
	}
	
	@Test
	public void calls_user_request_listener_with_selected_id_for_rename(){
		when(menuMock.getItem(R.id.rename_menu_item)).thenReturn(renameItemMock);
		
		when(positionTranslatorMock.getId(1)).thenReturn(ITEM_ID1);
		
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		when(renameItemMock.getItemId()).thenReturn(R.id.rename_menu_item);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(null, 1, 1, true);
		
		sutAsMultiChoiceModeListener.onActionItemClicked(actionModeMock, renameItemMock);
		
		verify(userRequestListenerMock).renameItem(ITEM_ID1);
	}
	
}
