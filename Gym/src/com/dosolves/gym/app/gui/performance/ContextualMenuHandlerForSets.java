package com.dosolves.gym.app.gui.performance;

import android.view.ActionMode;

import com.dosolves.gym.app.gui.ActionModeStarter;
import com.dosolves.gym.app.gui.ContextualMenuHandlerBase;
import com.dosolves.gym.domain.performance.Set;

public class ContextualMenuHandlerForSets extends ContextualMenuHandlerBase implements SetContextualMenuHandler {

	private ActionModeStarter actionModeStarter;
	private ActionMode actionMode;

	public ContextualMenuHandlerForSets(ActionModeStarter actionModeStarter) {
		this.actionModeStarter = actionModeStarter;
	}

	@Override
	public void setSetCheckedState(Set set, boolean checked) {
		if(firstSetToBeSelected(checked))
			initActionMode();
		
		handleItemSelectionChanged(actionMode, set.getId(), checked);
		
		if(noMoreSetsSelected())
			endActionMode();
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

}
