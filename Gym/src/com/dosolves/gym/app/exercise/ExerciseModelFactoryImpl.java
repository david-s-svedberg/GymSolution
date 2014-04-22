package com.dosolves.gym.app.exercise;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.ads.ContextRouterActivityStarter;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.exercise.gui.ContextExerciseOpener;
import com.dosolves.gym.app.gui.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.YesNoDialog;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.app.gui.RenameItemAlertDialogShower;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.data.DataAccess;
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

	private UserAskerImpl userAsker;

	@Override
	public ArrayAdapter<Exercise> createAdapter(Context context) {
		return new ArrayAdapter<Exercise>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public ExerciseController createController(Context context, ArrayAdapter<Exercise> adapter, CurrentCategoryHolder holder){
		
		ExerciseRetriever retriever = createRetriever();
		ExerciseUpdater updater = createUpdater();
		
		CreateItemDialogShower createExercisedialogShower = new CreateItemAlertDialogShower(context, context.getString(R.string.create_exercise));
		ItemOptionMenuDialogShower categoryOptionMenuDialog = new ItemOptionMenuAlertDialogShower(context);
		ExerciseOpener categoryOpener = new ContextExerciseOpener(context);
		RenameDialogShower renameDialogShower = new RenameItemAlertDialogShower(context, context.getString(R.string.rename_exercise));
		
		return new ExerciseController(adapter, retriever, createExercisedialogShower, updater, categoryOptionMenuDialog, categoryOpener, holder,renameDialogShower);
		
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

	@Override
	public UserAskerImpl getUserAsker() {
		if(userAsker == null)
			throw new IllegalStateException();
		
		return userAsker;
	}

	@Override
	public UserAskerImpl createUserAsker(Activity activity) {
		YesNoDialog dialog = createDialog();
		
		userAsker = new UserAskerImpl(new ContextRouterActivityStarter(activity), dialog);
		return userAsker;
	}
	
	private YesNoDialog createDialog() {
		YesNoDialog dialog = new YesNoDialog();
		
		Bundle arguments = new Bundle();
		arguments.putInt(YesNoDialog.TITLE_KEY, R.string.exercise_has_sets);
		arguments.putInt(YesNoDialog.MESSAGE_KEY, R.string.delete_anyway);
		dialog.setArguments(arguments);
		
		return dialog;
	}
	
}
