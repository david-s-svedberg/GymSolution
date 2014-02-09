package com.dosolves.gym.domain;

import com.dosolves.gym.app.gui.RenameDialogRequestedCallback;

public interface ItemOptionMenuDialogShower {

	void show(int itemPosition, ItemShouldBeDeletedCallback deleteCallback, RenameDialogRequestedCallback renameCallback);

}
