package com.dosolves.gym.app.performance.database;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.category.data.CategoryStructureGiver;
import com.dosolves.gym.domain.exercise.data.ExerciseStructureGiver;
import com.dosolves.gym.domain.performance.data.SetStructureGiver;

public class SetDbStructureGiver implements DbStructureGiver {

private Map<String, Integer> indexes;
	
	public SetDbStructureGiver(){
		indexes = new HashMap<String, Integer>();
		indexes.put(SetStructureGiver.ID_PROPERTY_NAME, 0);
		indexes.put(SetStructureGiver.EXERCISE_ID_PROPERTY_NAME, 1);
		indexes.put(SetStructureGiver.REPS_PROPERTY_NAME, 2);
		indexes.put(SetStructureGiver.WEIGHT_PROPERTY_NAME, 3);
		indexes.put(SetStructureGiver.DATE_PROPERTY_NAME, 4);
		
	}
	
	@Override
	public String[] getAllColumns() {
		return new String[]{SetStructureGiver.ID_PROPERTY_NAME, SetStructureGiver.EXERCISE_ID_PROPERTY_NAME, SetStructureGiver.REPS_PROPERTY_NAME, SetStructureGiver.WEIGHT_PROPERTY_NAME, SetStructureGiver.DATE_PROPERTY_NAME};		
	}

	@Override
	public String getTypeNamePlural() {
		return SetStructureGiver.SET_TYPE_NAME_PLURAL;
	}

	@Override
	public int getColumnIndex(String columnName) {
		return indexes.get(columnName);
	}

	@Override
	public String getCreateSQL() {
		return String.format("CREATE TABLE %s ( ", SetStructureGiver.SET_TYPE_NAME_PLURAL) +
				   String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT, ", SetStructureGiver.ID_PROPERTY_NAME) +
				   String.format("%s INTEGER, ", SetStructureGiver.EXERCISE_ID_PROPERTY_NAME) +
				   String.format("%s INTEGER, ", SetStructureGiver.REPS_PROPERTY_NAME) +
				   String.format("%s REAL, ", SetStructureGiver.WEIGHT_PROPERTY_NAME) +
				   String.format("%s INTEGER, ", SetStructureGiver.DATE_PROPERTY_NAME) +
				   String.format("FOREIGN KEY (%s) REFERENCES %s(%s) ", SetStructureGiver.EXERCISE_ID_PROPERTY_NAME, 
						   											    ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL, 
						   											    ExerciseStructureGiver.ID_PROPERTY_NAME) +
				   ")";
	}

}
