package com.dosolves.gym.domain.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.exercise.SetStructureGiver;

public class CursorSetFactory {

	private DbStructureGiver setDbStructureGiver;

	public CursorSetFactory(DbStructureGiver setDbStructureGiver) {
		this.setDbStructureGiver = setDbStructureGiver;
	}

	public List<Set> CreateSets(GymCursor cursor) {
		List<Set> sets = new ArrayList<Set>();
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			int id = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.ID_PROPERTY_NAME));
			int exerciseId = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.EXERCISE_ID_PROPERTY_NAME));
			int reps = cursor.getInt(setDbStructureGiver.getColumnIndex(SetStructureGiver.REPS_PROPERTY_NAME));
			double weight = cursor.getDouble(setDbStructureGiver.getColumnIndex(SetStructureGiver.WEIGHT_PROPERTY_NAME));
			Date date = new Date(cursor.getLong(setDbStructureGiver.getColumnIndex(SetStructureGiver.DATE_PROPERTY_NAME))); 
			
			sets.add(new Set(id,exerciseId,reps,weight,date));
			
			cursor.moveToNext();
		}
		cursor.close();
		
		return sets;
	}

}
