package com.dosolves.gym.app.gui.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.gui.ContextualMenuHandlerForListItems;
import com.dosolves.gym.app.gui.PositionToIdTranslator;
import com.dosolves.gym.domain.UserRequestListener;

public class ContextualMenuHandlerForListItemsTest {
	
	private static final int ITEM_ID2 = 2;
	private static final int ITEM_ID1 = 1;
	
	ContextualMenuHandler sut;
	MultiChoiceModeListener sutAsMultiChoiceModeListener;

	@Mock
	Menu menuMock;
	@Mock
	MenuItem editItemMock;
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
	@Mock
	Drawable iconMock;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		ContextualMenuHandlerForListItems sutImpl = new ContextualMenuHandlerForListItems(positionTranslatorMock);
		sut = sutImpl;
		sut.addUserRequestListener(userRequestListenerMock);
		sutAsMultiChoiceModeListener = sutImpl;
	}
	
	@Test
	public void disables_rename_menu_item_if_more_then_one_item_is_selected(){
		when(positionTranslatorMock.getId(1)).thenReturn(1);
		when(positionTranslatorMock.getId(2)).thenReturn(2);
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(editItemMock);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		when(editItemMock.getIcon()).thenReturn(iconMock);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 2, 2, true);
		
		verify(editItemMock).setEnabled(false);
		verify(iconMock).setAlpha(130);
	}
	
	@Test
	public void enables_rename_menu_item_when_one_item_is_selected(){
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(editItemMock);
		when(positionTranslatorMock.getId(1)).thenReturn(1);
		when(positionTranslatorMock.getId(2)).thenReturn(2);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		when(editItemMock.getIcon()).thenReturn(iconMock);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 2, 2, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 2, 2, false);
		
		verify(editItemMock,times(2)).setEnabled(true);
		verify(editItemMock).setEnabled(false);
		verify(iconMock, atLeast(1)).setAlpha(255);
	}
	
	@Test
	public void calls_user_request_listener_with_copy_of_selected_ids_for_delete(){
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(editItemMock);
		when(editItemMock.getIcon()).thenReturn(iconMock);
		
		when(positionTranslatorMock.getId(1)).thenReturn(ITEM_ID1);
		when(positionTranslatorMock.getId(2)).thenReturn(ITEM_ID2);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		when(deleteItemMock.getItemId()).thenReturn(R.id.delete_menu_item);
		
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 1, 1, true);
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 2, 2, true);
		
		sutAsMultiChoiceModeListener.onActionItemClicked(actionModeMock, deleteItemMock);
		
		verify(userRequestListenerMock).deleteItems(argThat(new ArgumentMatcher<List<Integer>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object argument) {
				passedIndeleteIds = (List<Integer>) argument;
				return passedIndeleteIds.size() == 2 && passedIndeleteIds.contains(ITEM_ID1) && passedIndeleteIds.contains(ITEM_ID2);
			}
		}));
		sutAsMultiChoiceModeListener.onDestroyActionMode(actionModeMock);
		assertEquals("SelectedItems was passed as reference, must be a copy to avoid it beeing cleared before the actual deletion",2, passedIndeleteIds.size());
	}
	
	private List<Integer> passedIndeleteIds;
	
	@Test
	public void calls_user_request_listener_with_selected_id_for_edit(){
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(editItemMock);
		when(editItemMock.getIcon()).thenReturn(iconMock);
		
		when(positionTranslatorMock.getId(1)).thenReturn(ITEM_ID1);
		
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		
		when(editItemMock.getItemId()).thenReturn(R.id.edit_menu_item);
		
		sutAsMultiChoiceModeListener.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsMultiChoiceModeListener.onItemCheckedStateChanged(actionModeMock, 1, 1, true);
		
		sutAsMultiChoiceModeListener.onActionItemClicked(actionModeMock, editItemMock);
		
		verify(userRequestListenerMock).editItem(ITEM_ID1);
	}
	
}
