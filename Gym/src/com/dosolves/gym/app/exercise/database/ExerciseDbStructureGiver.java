package com.dosolves.gym.app.exercise.database;

import java.util.HashMap;
import java.util.Map;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.category.data.CategoryStructureGiver;
import com.dosolves.gym.domain.exercise.data.ExerciseStructureGiver;

public class ExerciseDbStructureGiver implements DbStructureGiver {

	private Map<String, Integer> indexes;
	
	public ExerciseDbStructureGiver(){
		indexes = new HashMap<String, Integer>();
		indexes.put(ExerciseStructureGiver.ID_PROPERTY_NAME, 0);
		indexes.put(ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, 1);
		indexes.put(ExerciseStructureGiver.NAME_PROPERTY_NAME, 2);
	}
	
	@Override
	public String[] getAllColumns() {
		return new String[]{ExerciseStructureGiver.ID_PROPERTY_NAME,ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, ExerciseStructureGiver.NAME_PROPERTY_NAME};
	}

	@Override
	public String getTypeNamePlural() {
		return ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL;
	}

	@Override
	public int getColumnIndex(String columnName) {
		return indexes.get(columnName);
	}

	@Override
	public String getCreateSQL() {
		return String.format("CREATE TABLE %s ( ", ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL) +
			   String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT, ", ExerciseStructureGiver.ID_PROPERTY_NAME) +
			   String.format("%s INTEGER, ", ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME) +
			   String.format("%s TEXT, ", ExerciseStructureGiver.NAME_PROPERTY_NAME) +
			   String.format("FOREIGN KEY (%s) REFERENCES %s(%s) ", ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME, 
					   											   CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL, 
					   											   CategoryStructureGiver.ID_PROPERTY_NAME) +
			   ")";
	}

}
