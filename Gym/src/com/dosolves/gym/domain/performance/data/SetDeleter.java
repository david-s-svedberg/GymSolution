package com.dosolves.gym.domain.performance.data;

import com.dosolves.gym.domain.ItemDeleter;

public class SetDeleter implements ItemDeleter {

	private SetUpdater setUpdater;

	public SetDeleter(SetUpdater setUpdater) {
		this.setUpdater = setUpdater;
	}

	@Override
	public void deleteItem(int setId) {
		setUpdater.delete(setId);
	}

}
