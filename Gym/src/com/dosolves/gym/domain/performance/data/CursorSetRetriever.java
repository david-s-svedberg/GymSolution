package com.dosolves.gym.domain.performance.data;

import java.util.List;

import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.Set;

public class CursorSetRetriever implements SetRetriever {

	private DataAccess dataAccess;
	private CursorSetFactory setFactory;

	public CursorSetRetriever(DataAccess dataAccess,CursorSetFactory setFactory) {
		this.dataAccess = dataAccess;
		this.setFactory = setFactory;
	}

	@Override
	public List<Set> getSetsInExercise(int exerciseId) {
		return setFactory.createSets(dataAccess.get(SetStructureGiver.SET_TYPE_NAME_PLURAL,SetStructureGiver.EXERCISE_ID_PROPERTY_NAME,exerciseId));
	}

	@Override
	public Set getLastSetForExercise(Exercise exercise) {
		return setFactory.createSet(dataAccess.getLast(SetStructureGiver.SET_TYPE_NAME_PLURAL,SetStructureGiver.EXERCISE_ID_PROPERTY_NAME,exercise.getId(),SetStructureGiver.DATE_PROPERTY_NAME));
	}

	@Override
	public Set getSet(int setId) {
		return setFactory.createSet(dataAccess.get(SetStructureGiver.SET_TYPE_NAME_PLURAL, SetStructureGiver.ID_PROPERTY_NAME,setId));
	}

}
