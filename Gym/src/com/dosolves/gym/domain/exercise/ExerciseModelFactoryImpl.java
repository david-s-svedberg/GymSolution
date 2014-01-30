package com.dosolves.gym.domain.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.database.category.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.exercise.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.app.gui.category.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.exercise.ContextExerciseOpener;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;

public class ExerciseModelFactoryImpl implements ExerciseModelFactory {

	@Override
	public ArrayAdapter<Exercise> createAdapter(Context context) {
		return new ArrayAdapter<Exercise>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public ExerciseController createController(ExercisesActivity activity, ArrayAdapter<Exercise> adapter) {
		
		ExerciseRetriever retriever = createRetriever();
		ExerciseUpdater updater = createUpdater();
		
		CreateItemDialogShower createExercisedialogShower = new CreateItemAlertDialogShower(activity, activity.getString(R.string.create_exercise));
		ItemOptionMenuDialogShower categoryOptionMenuDialog = new ItemOptionMenuAlertDialogShower(activity);
		ExerciseOpener categoryOpener = new ContextExerciseOpener(activity);
		
		return new ExerciseController(adapter, retriever, createExercisedialogShower, updater, categoryOptionMenuDialog, categoryOpener, activity);
		
	}

	public ExerciseUpdater createUpdater() {
		return new ExerciseUpdaterImpl(createDataAccess());
	}

	public ExerciseRetriever createRetriever() {
		return new CursorExerciseRetriever(createDataAccess(), new CursorExerciseFactory(new ExerciseDbStructureGiver()));
	}

	private DataAccess createDataAccess() {
		return new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), new ExerciseDbStructureGiver(), new CategoryDbStructureGiver());
	}
}
