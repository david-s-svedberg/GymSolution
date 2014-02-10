package com.dosolves.gym.domain.performance;

public interface SetMenuDialogShower {

	void show(Set set, SetShouldBeDeletedCallback deleteCallback, EditSetDialogRequestedCallback showEditCallback);

}
