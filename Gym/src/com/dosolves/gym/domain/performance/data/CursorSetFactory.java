package com.dosolves.gym.domain.performance.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.performance.Set;

public class CursorSetFactory {

	private DbStructureGiver setDbStructureGiver;

	public CursorSetFactory(DbStructureGiver setDbStructureGiver) {
		this.setDbStructureGiver = setDbStructureGiver;
	}

	public List<Set> createSets(GymCursor cursor) {
		List<Set> sets = new ArrayList<Set>();
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			sets.add(createSetFromCursorAtCurrentPosition(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		
		return sets;
	}
	
	public Set createSet(GymCursor cursor) {
		Set ret = null;
		
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			ret = createSetFromCursorAtCurrentPosition(cursor);			
		}
		
		cursor.close();
		return ret;
	}

	private Set createSetFromCursorAtCurrentPosition(GymCursor cursor) {
		int id = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.ID_PROPERTY_NAME));
		int exerciseId = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.EXERCISE_ID_PROPERTY_NAME));
		int reps = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.REPS_PROPERTY_NAME));
		double weight = cursor.getDouble(setDbStructureGiver.getColumnIndex(SetStructureGiver.WEIGHT_PROPERTY_NAME));
		Date date = new Date(cursor.getLong(setDbStructureGiver.getColumnIndex(SetStructureGiver.DATE_PROPERTY_NAME))); 
		
		Set newSet = new Set(id,exerciseId,reps,weight,date);
		return newSet;
	}

}
