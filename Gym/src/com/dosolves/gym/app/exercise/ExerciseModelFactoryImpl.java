package com.dosolves.gym.app.exercise;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.exercise.gui.ContextExerciseOpener;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.gui.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.exercise.ExerciseOpener;
import com.dosolves.gym.domain.exercise.data.CursorExerciseFactory;
import com.dosolves.gym.domain.exercise.data.CursorExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdaterImpl;

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
