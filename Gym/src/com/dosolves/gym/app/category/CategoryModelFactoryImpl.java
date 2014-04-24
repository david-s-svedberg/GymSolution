package com.dosolves.gym.app.category;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.CurrentDateGiverImpl;
import com.dosolves.gym.app.ads.ContextRouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.category.gui.ContextCategoryOpener;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.app.gui.RenameItemAlertDialogShower;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.app.gui.YesNoDialog;
import com.dosolves.gym.app.performance.database.SetDbStructureGiver;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.category.data.CascadingCategoryDeleter;
import com.dosolves.gym.domain.category.data.CategoryItemHasSubItemsChecker;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.category.data.CategoryUpdaterImpl;
import com.dosolves.gym.domain.category.data.CursorCategoryFactory;
import com.dosolves.gym.domain.category.data.CursorCategoryRetriever;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.data.CascadingExerciseDeleter;
import com.dosolves.gym.domain.exercise.data.CursorExerciseFactory;
import com.dosolves.gym.domain.exercise.data.CursorExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdaterImpl;
import com.dosolves.gym.domain.exercise.data.HighLevelExerciseIdRetriever;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;
import com.dosolves.gym.domain.performance.data.CursorSetRetriever;
import com.dosolves.gym.domain.performance.data.HighLevelSetIdRetriever;
import com.dosolves.gym.domain.performance.data.SetDeleter;
import com.dosolves.gym.domain.performance.data.SetUpdaterImpl;

public class CategoryModelFactoryImpl implements CategoryModelFactory{

	private UserAskerImpl userAsker;

	@Override
	public ArrayAdapter<Category> createAdapter(Context context) {
		return new ArrayAdapter<Category>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public CategoryController createController(Context context, ArrayAdapter<Category> adapter) {
		CategoryRetriever retriever = createRetriever();
		CategoryUpdater updater = createUpdater();
		CreateItemDialogShower createCategorydialog = new CreateItemAlertDialogShower(context, context.getString(R.string.create_category));
		ItemOptionMenuDialogShower categoryOptionMenuDialog = new ItemOptionMenuAlertDialogShower(context);
		ContextCategoryOpener categoryOpener = new ContextCategoryOpener(context);
		RenameDialogShower renameDialogShower = new RenameItemAlertDialogShower(context, context.getString(R.string.rename_category));
		
		DeleteItemUseCaseController deleteItemUseCase = createDeleteUseCase(context, updater);
		
		return new CategoryController(adapter, retriever, createCategorydialog, updater, categoryOptionMenuDialog, categoryOpener,renameDialogShower, deleteItemUseCase);
	}
	
	private DeleteItemUseCaseController createDeleteUseCase(Context context, CategoryUpdater categoryUpdater) {
		DataAccess dataAccess = createDataAccess();
		ExerciseUpdaterImpl exerciseUpdater = new ExerciseUpdaterImpl(dataAccess);
		CurrentDateGiverImpl currentDateGiver = new CurrentDateGiverImpl();
		SetUpdaterImpl setUpdater = new SetUpdaterImpl(dataAccess, currentDateGiver);
		SetDeleter setDeleter = new SetDeleter(setUpdater);
		SetDbStructureGiver setDbStructureGiver = new SetDbStructureGiver();
		CursorSetFactory setFactory = new CursorSetFactory(setDbStructureGiver);
		CursorSetRetriever setRetriever = new CursorSetRetriever(dataAccess,  setFactory);
		HighLevelSetIdRetriever setIdRetriever = new HighLevelSetIdRetriever(setRetriever);
		CascadingExerciseDeleter exerciseDeleter = new CascadingExerciseDeleter(setIdRetriever, setDeleter, exerciseUpdater);
		ExerciseDbStructureGiver exerciseDbStructureGiver = new ExerciseDbStructureGiver();
		CursorExerciseFactory exerciseFactory = new CursorExerciseFactory(exerciseDbStructureGiver);
		CursorExerciseRetriever exerciseRetriever = new CursorExerciseRetriever(dataAccess, exerciseFactory);
		HighLevelExerciseIdRetriever exerciseIdRetriever = new HighLevelExerciseIdRetriever(exerciseRetriever);
		CascadingCategoryDeleter categoryDeleter = new CascadingCategoryDeleter(exerciseIdRetriever, exerciseDeleter, categoryUpdater);
		UserAskerImpl userAsker = createUserAsker(context);
		CategoryItemHasSubItemsChecker categoryItemHasSubItemsChecker = new CategoryItemHasSubItemsChecker(dataAccess);
		DeleteItemUseCaseController deleteItemUseCase = new DeleteItemUseCaseControllerImpl(categoryItemHasSubItemsChecker, userAsker, categoryDeleter);
		
		return deleteItemUseCase;
	}

	public CategoryUpdater createUpdater() {
		return new CategoryUpdaterImpl(createDataAccess());
	}

	public CategoryRetriever createRetriever() {
		CursorCategoryFactory categoryFactory = new CursorCategoryFactory(new CategoryDbStructureGiver());
		CategoryRetriever retriever = new CursorCategoryRetriever(createDataAccess(), categoryFactory);
		return retriever;
	}

	private DataAccess createDataAccess() {
		return new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), new CategoryDbStructureGiver(), new ExerciseDbStructureGiver(), new SetDbStructureGiver());
	}

	@Override
	public UserAskerImpl getUserAsker() {
		if (userAsker == null)
			throw new IllegalStateException();
		
		return userAsker;
	}

	@Override
	public UserAskerImpl createUserAsker(Context context) {
		YesNoDialog dialog = createDialog();
		
		userAsker = new UserAskerImpl(new ContextRouterActivityStarter(context), dialog, RouteModule.CATEGORY);
		return userAsker;
	}

	private YesNoDialog createDialog() {
		YesNoDialog dialog = new YesNoDialog();
		
		Bundle arguments = new Bundle();
		arguments.putInt(YesNoDialog.TITLE_KEY, R.string.category_has_exercises);
		arguments.putInt(YesNoDialog.MESSAGE_KEY, R.string.delete_anyway);
		dialog.setArguments(arguments);
		
		return dialog;
	}
	
}
