package com.dosolves.gym.app.performance.gui;

import com.dosolves.gym.app.gui.ActionModeEndingListener;
import com.dosolves.gym.domain.performance.Set;

public interface SetContextualMenuHandler {

	void setSetCheckedState(Set set, boolean checked);
	void addActionModeEndingListener(ActionModeEndingListener actionModeEndingListener);

}
