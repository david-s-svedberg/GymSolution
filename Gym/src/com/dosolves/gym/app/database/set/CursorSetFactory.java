package com.dosolves.gym.app.database.set;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.set.Set;

public class CursorSetFactory {

	private DbStructureGiver setDbStructureGiver;

	public CursorSetFactory(DbStructureGiver setDbStructureGiver) {
		this.setDbStructureGiver = setDbStructureGiver;
	}

	public List<Set> CreateSets(GymCursor cursorMock) {
		List<Set> sets = new ArrayList<Set>();
		return sets;
	}

}
