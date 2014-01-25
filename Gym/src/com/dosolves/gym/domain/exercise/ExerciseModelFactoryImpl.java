package com.dosolves.gym.domain.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.database.exercise.CursorExerciseFactory;
import com.dosolves.gym.app.database.exercise.CursorExerciseRetriever;
import com.dosolves.gym.app.database.exercise.ExerciseDbStructureGiver;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.DbStructureGiver;

public class ExerciseModelFactoryImpl implements ExerciseModelFactory {

	@Override
	public ArrayAdapter<Exercise> createAdapter(Context context) {
		return new ArrayAdapter<Exercise>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public ExerciseController createController(Context context,	ArrayAdapter<Exercise> adapter) {
		DbStructureGiver exerciseDbStructure = new ExerciseDbStructureGiver();
		DataAccess dao = new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), exerciseDbStructure);
		CursorExerciseFactory exerciseFactory = new CursorExerciseFactory(exerciseDbStructure);
		
		ExerciseRetriever retriever = new CursorExerciseRetriever(dao, exerciseFactory);
//		CategoryUpdater updater = new CategoryUpdaterImpl(dao);
//		CreateItemDialogShower createCategorydialog = new CreateCategoryAlertDialog(context);
//		ItemOptionMenuDialogShower categoryOptionMenuDialog = new CategoryOptionMenuAlertDialog(context);
//		ContextCategoryOpener categoryOpener = new ContextCategoryOpener(context);
		
		//return new ExerciseController(adapter, retriever, createCategorydialog, updater, categoryOptionMenuDialog, categoryOpener);
		return null;
	}

}
