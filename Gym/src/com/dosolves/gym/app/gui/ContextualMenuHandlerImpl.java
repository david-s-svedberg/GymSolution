package com.dosolves.gym.app.gui;

import java.util.ArrayList;
import java.util.List;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.UserRequestListener;
import com.dosolves.gym.domain.UserRequestObservable;
import com.dosolves.gym.domain.UserRequestObservableImpl;

public class ContextualMenuHandlerImpl implements ContextualMenuHandler {

	private static final int DISABLED_ALPHA = 130;
	private static final int FULL_ALPHA = 255;
	
	private Menu menu;
	private List<Integer> selectedItems;
	private PositionToIdTranslator positionTranslator;
	private UserRequestObservable userRequestListeners = new UserRequestObservableImpl();

	public ContextualMenuHandlerImpl(PositionToIdTranslator positionTranslator) {
		this.positionTranslator = positionTranslator;
		selectedItems = new ArrayList<Integer>();
	}

	@Override
	public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
		switch (menuItem.getItemId()) {
        case R.id.delete_menu_item:
            userRequestListeners.notifyDeleteItems(getCopyOfSelectedItems());
            actionMode.finish();
            return true;
        case R.id.rename_menu_item:
            userRequestListeners.notifyRenameItem(selectedItems.get(0));
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

	@Override
	public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
		updateSelectedItems(position, checked);
		updateMenuItemEnabledStates();
		updateTitle(actionMode);
	}

	private void updateTitle(ActionMode actionMode) {
		actionMode.setTitle(numberOfSelectedItems());
	}

	private String numberOfSelectedItems() {
		return Integer.toString(selectedItems.size());
	}

	private void updateSelectedItems(int position, boolean checked) {
		if(checked)
			selectedItems.add(positionTranslator.getId(position));
		else
			selectedItems.remove(positionTranslator.getId(position));
	}

	private void updateMenuItemEnabledStates() {
		MenuItem renameMenuItem = menu.findItem(R.id.rename_menu_item);
		if(selectedItems.size()>1){
			renameMenuItem.setEnabled(false);
			renameMenuItem.getIcon().setAlpha(DISABLED_ALPHA);
		}
		else{
			renameMenuItem.setEnabled(true);
			renameMenuItem.getIcon().setAlpha(FULL_ALPHA);
		}
	}

	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequestListeners.registerUserRequestListener(userRequestListener);	
	}

}
