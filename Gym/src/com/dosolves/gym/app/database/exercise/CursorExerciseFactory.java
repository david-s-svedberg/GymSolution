package com.dosolves.gym.app.database.exercise;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseStructureGiver;

public class CursorExerciseFactory {

	private DbStructureGiver exerciseDbStructureGiver;

	public CursorExerciseFactory(DbStructureGiver exerciseDbStructureGiver) {
		this.exerciseDbStructureGiver = exerciseDbStructureGiver;
	}

	public List<Exercise> CreateExercises(Cursor cursor) {
		List<Exercise> exercises = new ArrayList<Exercise>();
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			int id = cursor.getInt(exerciseDbStructureGiver.getColumnIndex(ExerciseStructureGiver.ID_PROPERTY_NAME));
			int categoryId = cursor.getInt(exerciseDbStructureGiver.getColumnIndex(ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME));
			String name = cursor.getString(exerciseDbStructureGiver.getColumnIndex(ExerciseStructureGiver.NAME_PROPERTY_NAME));
			
			exercises.add(new Exercise(id,categoryId, name));
			
			cursor.moveToNext();
		}
		cursor.close();

		return exercises;
	}

}
