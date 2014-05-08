package com.dosolves.gym.app.performance.gui;

import java.util.ArrayList;
import java.util.List;

import android.view.ActionMode;

import com.dosolves.gym.app.gui.ActionModeEndingListener;
import com.dosolves.gym.app.gui.ActionModeStarter;
import com.dosolves.gym.app.gui.ContextualMenuHandlerBase;
import com.dosolves.gym.domain.performance.Set;

public class ContextualMenuHandlerForSets extends ContextualMenuHandlerBase implements SetContextualMenuHandler {

	private ActionModeStarter actionModeStarter;
	private ActionMode actionMode;
	private List<ActionModeEndingListener> actionModeEndingListeners;

	public ContextualMenuHandlerForSets(ActionModeStarter actionModeStarter) {
		this.actionModeStarter = actionModeStarter;
		actionModeEndingListeners = new ArrayList<ActionModeEndingListener>();
	}

	@Override
	public void setSetCheckedState(Set set, boolean checked) {
		if(firstSetToBeSelected(checked))
			initActionMode();
		
		handleItemSelectionChanged(actionMode, set.getId(), checked);
		
		if(noMoreSetsSelected())
			endActionMode();
	}
	
	@Override
	public void onDestroyActionMode(ActionMode actionMode) {
		super.onDestroyActionMode(actionMode);
		notifyActionModeEnding();
	}

	private void notifyActionModeEnding() {
		for(ActionModeEndingListener listener: actionModeEndingListeners)
			listener.onActionModeEnding();
	}

	private void endActionMode() {
		actionMode.finish();
	}

	private boolean noMoreSetsSelected() {
		return selectedItems.size() == 0;
	}

	private void initActionMode() {
		this.actionMode = actionModeStarter.startActionMode(this);
	}

	private boolean firstSetToBeSelected(boolean checked) {
		return checked && selectedItems.size() == 0;
	}

	@Override
	public void addActionModeEndingListener(ActionModeEndingListener listener) {
		actionModeEndingListeners.add(listener);
	}

}
