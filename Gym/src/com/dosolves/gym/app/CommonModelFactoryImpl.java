package com.dosolves.gym.app;

import android.content.Context;

import com.dosolves.gym.app.ads.ContextRouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.gui.ContextualMenuHandlerForListItems;
import com.dosolves.gym.app.gui.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.RenameItemAlertDialogShower;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.app.performance.database.SetDbStructureGiver;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentDateGiver;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.data.CascadingCategoryDeleter;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.category.data.CategoryUpdaterImpl;
import com.dosolves.gym.domain.category.data.CursorCategoryFactory;
import com.dosolves.gym.domain.category.data.CursorCategoryRetriever;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.data.CascadingExerciseDeleter;
import com.dosolves.gym.domain.exercise.data.CursorExerciseFactory;
import com.dosolves.gym.domain.exercise.data.CursorExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseIdRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdaterImpl;
import com.dosolves.gym.domain.exercise.data.HighLevelExerciseIdRetriever;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;
import com.dosolves.gym.domain.performance.data.CursorSetRetriever;
import com.dosolves.gym.domain.performance.data.HighLevelSetIdRetriever;
import com.dosolves.gym.domain.performance.data.SetDeleter;
import com.dosolves.gym.domain.performance.data.SetIdRetriever;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;
import com.dosolves.gym.domain.performance.data.SetUpdaterImpl;

public class CommonModelFactoryImpl implements CommonModelFactory {

	private DataAccess dataAccess;

	private CategoryRetriever categoryRetriever;
	private CategoryUpdater categoryUpdater;
	
	private ExerciseRetriever exerciseRetriever;
	private ExerciseUpdater exerciseUpdater;
	
	private SetRetriever retriever;
	private SetUpdater setUpdater;

	private ItemDeleter categoryDeleter;
	private ItemDeleter exerciseDeleter;
	private ItemDeleter setDeleter;

	private PreferenceRetriever preferenceRetriever;
	private RouterActivityStarter routerActivityStarter;


	@Override
	public DataAccess getDataAccess() {
		if(dataAccess == null)
			dataAccess = createDataAccess();
		
		return dataAccess;
	}
	
	private DataAccess createDataAccess() {
		return new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), new ExerciseDbStructureGiver(), new CategoryDbStructureGiver(), new SetDbStructureGiver());
	}
	
	@Override
	public SetRetriever getSetRetriever() {
		if (retriever == null)
			retriever = createSetRetriever();
		
		return retriever;
	}
	
	private SetRetriever createSetRetriever() {
		DbStructureGiver setDbStructureGiver = new SetDbStructureGiver();
		CursorSetFactory factory = new CursorSetFactory(setDbStructureGiver);
		return new CursorSetRetriever(getDataAccess(), factory);
	}
	
	@Override
	public SetUpdater getSetUpdater() {
		if (setUpdater == null)
			setUpdater = createSetUpdater();
		
		return setUpdater;
	}

	private SetUpdater createSetUpdater() {
		CurrentDateGiver currentDateGiver = new CurrentDateGiverImpl();
		SetUpdater updater = new SetUpdaterImpl(getDataAccess(), currentDateGiver);
		return updater;
	}

	@Override
	public ExerciseRetriever getExerciseRetriever() {
		if(exerciseRetriever == null)
			exerciseRetriever = createExerciseRetriever();
		
		return exerciseRetriever;
	}

	private ExerciseRetriever createExerciseRetriever() {
		DataAccess dataAccess = getDataAccess();
		ExerciseDbStructureGiver dbStructureGiver = new ExerciseDbStructureGiver();
		CursorExerciseFactory exerciseFactory = new CursorExerciseFactory(dbStructureGiver);
		
		return new CursorExerciseRetriever(dataAccess, exerciseFactory);
	}

	@Override
	public ExerciseUpdater getExerciseUpdater() {
		if(exerciseUpdater == null)
			exerciseUpdater = createExerciseUpdater();
		
		return exerciseUpdater;
	}

	private ExerciseUpdater createExerciseUpdater() {
		return new ExerciseUpdaterImpl(getDataAccess());
	}

	@Override
	public CreateItemDialogShower createCreateItemDialogShower(Context context, String title) {
		return new CreateItemAlertDialogShower(context, title);
	}
	
	@Override
	public RenameDialogShower createRenameDialogShower(Context context, String title) {
		return new RenameItemAlertDialogShower(context, title);
	}

	@Override
	public ItemDeleter getExerciseDeleter() {
		if(exerciseDeleter == null)
			exerciseDeleter = createExerciseDeleter();
		
		return exerciseDeleter;
	}

	private ItemDeleter createExerciseDeleter() {
		ItemDeleter setDeleter = getSetDeleter();
		SetRetriever setRetriever = getSetRetriever();
		SetIdRetriever setIdRetriever = new HighLevelSetIdRetriever(setRetriever);
		ExerciseUpdater exerciseUpdater = getExerciseUpdater();
		
		return new CascadingExerciseDeleter(setIdRetriever, setDeleter, exerciseUpdater);
	}

	@Override
	public CategoryRetriever getCategoryRetriever() {
		if(categoryRetriever == null)
			categoryRetriever = createCategoryRetriever();
		
		return categoryRetriever;
	}

	private CategoryRetriever createCategoryRetriever() {
		CategoryDbStructureGiver categoryDbStructureGiver = new CategoryDbStructureGiver();
		CursorCategoryFactory categoryFactory = new CursorCategoryFactory(categoryDbStructureGiver);
		DataAccess dataAccess = getDataAccess();
		
		return new CursorCategoryRetriever(dataAccess, categoryFactory);
	}

	@Override
	public CategoryUpdater getCategoryUpdater() {
		if(categoryUpdater == null)
			categoryUpdater = createCategoryUpdater();
		
		return categoryUpdater;
	}

	private CategoryUpdater createCategoryUpdater() {
		DataAccess dataAccess = getDataAccess();
		return new CategoryUpdaterImpl(dataAccess);
	}

	@Override
	public ItemDeleter getCategoryDeleter() {
		if(categoryDeleter == null)
			categoryDeleter = createCategoryDeleter();
		
		return categoryDeleter;
	}

	private ItemDeleter createCategoryDeleter() {
		ItemDeleter exerciseDeleter = getExerciseDeleter();
		ExerciseRetriever exerciseRetriever = getExerciseRetriever();
		ExerciseIdRetriever exerciseIdRetriever = new HighLevelExerciseIdRetriever(exerciseRetriever);
		CategoryUpdater categoryUpdater = getCategoryUpdater();
		
		return new CascadingCategoryDeleter(exerciseIdRetriever, exerciseDeleter, categoryUpdater);
	}
	
	@Override
	public ItemDeleter getSetDeleter() {
		if(setDeleter == null)
			setDeleter = createSetDeleter();
		
		return setDeleter;
	}

	private ItemDeleter createSetDeleter() {
		SetUpdater setUpdater = getSetUpdater();
		return new SetDeleter(setUpdater);
	}

	@Override
	public ContextualMenuHandler createContextualMenuHandler(UserUpdateableItemsActivity activity) {
		return new ContextualMenuHandlerForListItems(activity);
	}

	@Override
	public PreferenceRetriever getpreferenceRetriever(Context context) {
		if(preferenceRetriever == null)
			preferenceRetriever = createPreferenceRetriever(context);
		
		return preferenceRetriever; 
	}

	private PreferenceRetriever createPreferenceRetriever(Context context) {
		return new ContextPreferenceRetriever(context);
	}

	@Override
	public RouterActivityStarter getRouterActivityStarter(Context context) {
		if (routerActivityStarter == null)
			routerActivityStarter = createRouterActivityStarter(context);
		
		return routerActivityStarter;
	}

	private RouterActivityStarter createRouterActivityStarter(Context context) {
		return new ContextRouterActivityStarter(context);
	}
	
}
