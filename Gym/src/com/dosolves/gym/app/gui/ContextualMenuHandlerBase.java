package com.dosolves.gym.app.gui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.UserRequestListener;
import com.dosolves.gym.domain.UserRequestObservable;
import com.dosolves.gym.domain.UserRequestObservableImpl;
import com.dosolves.gym.utils.ResourcesUtils;

@SuppressLint("DefaultLocale")
public abstract class ContextualMenuHandlerBase implements ContextualMenuHandler,ActionMode.Callback  {

	private static final int DISABLED_ALPHA = 130;
	private static final int FULL_ALPHA = 255;
	
	private Menu menu;
	protected List<Integer> selectedItems;
	private UserRequestObservable userRequestListeners = new UserRequestObservableImpl();

	public ContextualMenuHandlerBase() {
		selectedItems = new ArrayList<Integer>();
	}

	@Override
	public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
		switch (menuItem.getItemId()) {
	    case R.id.delete_menu_item:
	        userRequestListeners.notifyDeleteItems(getCopyOfSelectedItems());
	        actionMode.finish();
	        return true;
	    case R.id.edit_menu_item:
	        userRequestListeners.notifyEditItem(selectedItems.get(0));
	        actionMode.finish();
	        return true;
	    default:
	        return false;
	}
	}

	private List<Integer> getCopyOfSelectedItems() {
		List<Integer> copy = new ArrayList<Integer>(selectedItems.size());
		
		for(Integer selectedId:selectedItems)
			copy.add(selectedId);
		
		return copy;
	}

	@Override
	public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
		MenuInflater inflater = actionMode.getMenuInflater();
	    inflater.inflate(R.menu.item_context, menu);
	    this.menu = menu;
	    return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode actionMode) {
		selectedItems.clear();
	}

	@Override
	public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
		return false;
	}

	protected void updateTitle(ActionMode actionMode) {
		actionMode.setTitle(createTitleString());
	}

	private String createTitleString() {
		
		int numberOfSelectedItems = selectedItems.size();
		
		String selectedString = getCorrectPluralizedSelectionString(numberOfSelectedItems);
			
		return String.format("%d %s",numberOfSelectedItems, selectedString);
	}

	private String getCorrectPluralizedSelectionString(int numberOfSelectedItems) {
		String correctSelectedString = null;
		
		if(numberOfSelectedItems > 1)
			correctSelectedString = ResourcesUtils.getString(R.string.selected_plural);
		else
			correctSelectedString = ResourcesUtils.getString(R.string.selected_singular);
		
		return correctSelectedString;
	}

	protected void updateSelectedItems(Integer id, boolean checked) {
		if(checked)
			selectedItems.add(id);
		else
			selectedItems.remove(id);
	}

	protected void updateMenuItemEnabledStates() {
		MenuItem editMenuItem = menu.findItem(R.id.edit_menu_item);
		if(selectedItems.size()>1){
			editMenuItem.setEnabled(false);
			editMenuItem.getIcon().setAlpha(DISABLED_ALPHA);
		}
		else{
			editMenuItem.setEnabled(true);
			editMenuItem.getIcon().setAlpha(FULL_ALPHA);
		}
	}

	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequestListeners.registerUserRequestListener(userRequestListener);	
	}

	protected void handleItemSelectionChanged(ActionMode actionMode, int id,
			boolean checked) {
				updateSelectedItems(id, checked);
				updateMenuItemEnabledStates();
				updateTitle(actionMode);
			}

}