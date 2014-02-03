package com.dosolves.gym.app;

import java.util.Date;

import com.dosolves.gym.domain.CurrentDateGiver;

public class CurrentDateGiverImpl implements CurrentDateGiver {

	@Override
	public long getCurrentDate() {
		return (new Date()).getTime();
	}

}
