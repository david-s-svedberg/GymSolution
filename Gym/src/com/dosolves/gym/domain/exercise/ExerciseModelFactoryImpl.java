package com.dosolves.gym.domain.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.database.exercise.CursorExerciseFactory;
import com.dosolves.gym.app.database.exercise.CursorExerciseRetriever;
import com.dosolves.gym.app.database.exercise.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.app.gui.category.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.exercise.ContextExerciseOpener;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;

public class ExerciseModelFactoryImpl implements ExerciseModelFactory {

	@Override
	public ArrayAdapter<Exercise> createAdapter(Context context) {
		return new ArrayAdapter<Exercise>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public ExerciseController createController(ExercisesActivity activity, ArrayAdapter<Exercise> adapter) {
		DbStructureGiver exerciseDbStructure = new ExerciseDbStructureGiver();
		DataAccess dao = new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), exerciseDbStructure);
		CursorExerciseFactory exerciseFactory = new CursorExerciseFactory(exerciseDbStructure);
		
		ExerciseRetriever retriever = new CursorExerciseRetriever(dao, exerciseFactory);
		ExerciseUpdater updater = new ExerciseUpdaterImpl(dao);
		CreateItemDialogShower createExercisedialogShower = new CreateItemAlertDialogShower(activity, activity.getString(R.string.create_exercise));
		ItemOptionMenuDialogShower categoryOptionMenuDialog = new ItemOptionMenuAlertDialogShower(activity);
		ExerciseOpener categoryOpener = new ContextExerciseOpener(activity);
		
		return new ExerciseController(adapter, retriever, createExercisedialogShower, updater, categoryOptionMenuDialog, categoryOpener, activity);
		
	}

}
