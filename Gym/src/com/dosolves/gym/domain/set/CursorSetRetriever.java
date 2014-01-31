package com.dosolves.gym.domain.set;

import java.util.List;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;

public class CursorSetRetriever implements SetRetriever {

	private DataAccess dataAccess;
	private CursorSetFactory setFactory;

	public CursorSetRetriever(DataAccess dataAccess,CursorSetFactory setFactory) {
		this.dataAccess = dataAccess;
		this.setFactory = setFactory;
	}

	@Override
	public List<Set> getSetsInExercise(Exercise exercise) {
		// TODO Auto-generated method stub
		return null;
	}

}
