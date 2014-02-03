package com.dosolves.gym.domain.performance.data;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.CurrentDateGiver;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.performance.Set;

public class SetUpdaterImpl implements SetUpdater {

	private DataAccess dataAccess;
	private CurrentDateGiver currentDateGiver;

	public SetUpdaterImpl(DataAccess dataAccess, CurrentDateGiver currentDateGiver) {
		this.dataAccess = dataAccess;
		this.currentDateGiver = currentDateGiver;
	}

	@Override
	public void create(int exerciseId, int reps, double weight) {
		dataAccess.create(SetStructureGiver.SET_TYPE_NAME_PLURAL, createValuesMap(exerciseId,reps,weight));
	}
	
	@Override
	public void delete(Set set) {
		dataAccess.delete(SetStructureGiver.SET_TYPE_NAME_PLURAL, SetStructureGiver.ID_PROPERTY_NAME, set.getId());
	}

	private Map<String, Object> createValuesMap(int exerciseId, int reps, double weight) {
		Map<String, Object> values = new HashMap<String, Object>();
		
		values.put(SetStructureGiver.EXERCISE_ID_PROPERTY_NAME, exerciseId);
		values.put(SetStructureGiver.REPS_PROPERTY_NAME, reps);
		values.put(SetStructureGiver.WEIGHT_PROPERTY_NAME, weight);
		values.put(SetStructureGiver.DATE_PROPERTY_NAME, currentDateGiver.getCurrentDate());
		
		return values;
	}

}
