package com.dosolves.gym.app.gui;

import com.dosolves.gym.domain.UserRequestListener;

import android.widget.AbsListView.MultiChoiceModeListener;

public interface ContextualMenuHandler extends MultiChoiceModeListener {
	void addUserRequestListener(UserRequestListener userRequestListener);
}
