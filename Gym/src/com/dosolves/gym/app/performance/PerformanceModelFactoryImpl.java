package com.dosolves.gym.app.performance;

import android.content.Context;

import com.dosolves.gym.app.CurrentDateGiverImpl;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.app.performance.database.SetDbStructureGiver;
import com.dosolves.gym.app.performance.gui.EditSetFragmentDialogShower;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.app.performance.gui.SetMenuAlertDialog;
import com.dosolves.gym.domain.CurrentDateGiver;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseControllerImpl;
import com.dosolves.gym.domain.performance.SetMenuDialogShower;
import com.dosolves.gym.domain.performance.data.CursorSetFactory;
import com.dosolves.gym.domain.performance.data.CursorSetRetriever;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdaterImpl;

public class PerformanceModelFactoryImpl implements PerformanceModelFactory {

	@Override
	public PerformanceAdapter createAdapter(Context context) {
		return new PerformanceAdapter(context);
	}

	@Override
	public PerformanceController createController(Context context, PerformanceAdapter adapter, CurrentExerciseHolder holder, FragmentManagerProvider fragmentManagerProvider) {
		DataAccess dataAccess = createDataAccess();
		SetRetriever retriever = createSetRetriever(dataAccess);
		CurrentDateGiver currentDateGiver = new CurrentDateGiverImpl();
		SetUpdaterImpl updater = new SetUpdaterImpl(dataAccess, currentDateGiver);
		PerformanceBuilder builder = new PerformanceBuilder();
		EditSetDialogShower editDialog = new EditSetFragmentDialogShower(fragmentManagerProvider);
		SetMenuDialogShower setMenuDialog = new SetMenuAlertDialog(context);
		
		return new PerformanceController(adapter, holder, retriever, builder, updater, editDialog,setMenuDialog);
	}

	@Override
	public SetLastResultUseCaseControllerImpl createSetLastResultUseCaseController(PerformanceActivity performanceActivity) {
		return new SetLastResultUseCaseControllerImpl(performanceActivity, createSetRetriever(createDataAccess()), performanceActivity);
	}


	private SetRetriever createSetRetriever(DataAccess dataAccess) {
		DbStructureGiver setDbStructureGiver = new SetDbStructureGiver();
		CursorSetFactory factory = new CursorSetFactory(setDbStructureGiver);
		SetRetriever retriever = new CursorSetRetriever(dataAccess, factory);
		return retriever;
	}
	
	private DataAccess createDataAccess() {
		return new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), new ExerciseDbStructureGiver(), new CategoryDbStructureGiver(), new SetDbStructureGiver());
	}
	
}
