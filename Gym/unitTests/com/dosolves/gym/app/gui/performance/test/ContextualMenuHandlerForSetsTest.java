package com.dosolves.gym.app.gui.performance.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.ActionModeEndingListener;
import com.dosolves.gym.app.gui.ActionModeStarter;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.performance.gui.ContextualMenuHandlerForSets;
import com.dosolves.gym.app.performance.gui.SetContextualMenuHandler;
import com.dosolves.gym.domain.UserRequestListener;
import com.dosolves.gym.domain.performance.Set;

public class ContextualMenuHandlerForSetsTest {

	private static final int SET_ID = 564;
	private static final int EXERCISE_ID = 4564;
	private static final int REPS = 12;
	private static final double WEIGHT = 50.5;
	
	ContextualMenuHandler sut;
	SetContextualMenuHandler sutAsSetContextualMenuHandler;
	ActionMode.Callback sutAsActionModeCallback;
	
	Set setMock;
	@Mock
	UserRequestListener userRequestListenerMock;
	@Mock
	ActionModeStarter actionModeStarterMock;
	@Mock
	Menu menuMock;
	@Mock
	MenuItem renameItemMock;
	@Mock
	ActionMode actionModeMock;
	@Mock
	MenuInflater menuInflaterMock;
	@Mock
	Drawable iconMock;
	@Mock
	ActionModeEndingListener actionModeEndingListenerMock1;
	@Mock
	ActionModeEndingListener actionModeEndingListenerMock2;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	
		setMock = new Set(SET_ID, EXERCISE_ID, REPS, WEIGHT, new Date());
		
		ContextualMenuHandlerForSets sutImpl = new ContextualMenuHandlerForSets(actionModeStarterMock);
		sut = sutImpl;
		sutAsSetContextualMenuHandler = sutImpl;
		sutAsActionModeCallback = sutImpl;
		
		sut.addUserRequestListener(userRequestListenerMock);
	
	}
	
	@Test
	public void starts_action_mode_when_first_set_is_selected(){
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(renameItemMock);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		when(renameItemMock.getIcon()).thenReturn(iconMock);
		when(actionModeStarterMock.startActionMode(sutAsActionModeCallback)).thenReturn(actionModeMock);
		
		sutAsActionModeCallback.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsSetContextualMenuHandler.setSetCheckedState(setMock, true);
		
		verify(actionModeStarterMock).startActionMode(sutAsActionModeCallback);
	}
	
	@Test
	public void stops_action_mode_when_last_set_is_deselected(){
		when(menuMock.findItem(R.id.edit_menu_item)).thenReturn(renameItemMock);
		when(actionModeMock.getMenuInflater()).thenReturn(menuInflaterMock);
		when(renameItemMock.getIcon()).thenReturn(iconMock);
		when(actionModeStarterMock.startActionMode(sutAsActionModeCallback)).thenReturn(actionModeMock);
		
		sutAsActionModeCallback.onCreateActionMode(actionModeMock, menuMock);
		
		sutAsSetContextualMenuHandler.setSetCheckedState(setMock, true);
		sutAsSetContextualMenuHandler.setSetCheckedState(setMock, false);
		
		verify(actionModeMock).finish();
	}
	
	@Test
	public void notifies_listeners_on_finish(){
		sutAsSetContextualMenuHandler.addActionModeEndingListener(actionModeEndingListenerMock1);
		sutAsSetContextualMenuHandler.addActionModeEndingListener(actionModeEndingListenerMock2);
		
		sutAsActionModeCallback.onDestroyActionMode(actionModeMock);
		
		verify(actionModeEndingListenerMock1).onActionModeEnding();
		verify(actionModeEndingListenerMock2).onActionModeEnding();
	}
	
}
