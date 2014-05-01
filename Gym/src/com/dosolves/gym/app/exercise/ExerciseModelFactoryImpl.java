package com.dosolves.gym.app.exercise;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.ads.ContextRouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.exercise.gui.ContextExerciseOpener;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.app.gui.YesNoDialog;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.exercise.ExerciseOpener;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.exercise.data.ExericseItemHasSubItemsChecker;

public class ExerciseModelFactoryImpl implements ExerciseModelFactory {

	private UserAskerImpl userAsker;
	private CommonModelFactory commonModelFactory;

	public ExerciseModelFactoryImpl(CommonModelFactory commonModelFactory) {
		this.commonModelFactory = commonModelFactory;
	}

	@Override
	public ArrayAdapter<Exercise> createAdapter(Context context) {
		return new ArrayAdapter<Exercise>(context, android.R.layout.simple_list_item_activated_1);
	}

	@Override
	public ExerciseController createController(Context context, ArrayAdapter<Exercise> adapter, CurrentCategoryHolder holder){
		
		ExerciseRetriever retriever = commonModelFactory.getExerciseRetriever();
		ExerciseUpdater updater = commonModelFactory.getExerciseUpdater();
		CreateItemDialogShower createExercisedialogShower = commonModelFactory.createCreateItemDialogShower(context, context.getString(R.string.create_exercise));
		RenameDialogShower renameDialogShower = commonModelFactory.createRenameDialogShower(context, context.getString(R.string.rename_exercise));
		
		DeleteItemUseCaseController deleteItemUseCase = createDeleteUseCase(context);
		ExerciseOpener exerciseOpener = new ContextExerciseOpener(context);
		
		return new ExerciseController(adapter, retriever, createExercisedialogShower, updater, exerciseOpener, holder,renameDialogShower, deleteItemUseCase);
	}

	private DeleteItemUseCaseController createDeleteUseCase(Context context) {
		DataAccess dataAccess = commonModelFactory.getDataAccess();
		ItemDeleter exerciseDeleter = commonModelFactory.getExerciseDeleter();
		
		UserAskerImpl userAsker = createUserAsker(context);
		ExericseItemHasSubItemsChecker exericseItemHasSubItemsChecker = new ExericseItemHasSubItemsChecker(dataAccess);
		DeleteItemUseCaseController deleteItemUseCase = new DeleteItemUseCaseControllerImpl(exericseItemHasSubItemsChecker, userAsker, exerciseDeleter);
		
		return deleteItemUseCase;
	}

	@Override
	public UserAskerImpl getUserAsker() {
		if(userAsker == null)
			throw new IllegalStateException();
		
		return userAsker;
	}

	@Override
	public UserAskerImpl createUserAsker(Context context) {
		YesNoDialog dialog = createDialog();
		
		userAsker = new UserAskerImpl(new ContextRouterActivityStarter(context), dialog, RouteModule.EXERCISE);
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
