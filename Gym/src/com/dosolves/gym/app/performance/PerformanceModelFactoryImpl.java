package com.dosolves.gym.app.performance;

import android.content.Context;

import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.app.performance.gui.ContextualMenuHandlerForSets;
import com.dosolves.gym.app.performance.gui.EditSetFragmentDialogShower;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseControllerImpl;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;

public class PerformanceModelFactoryImpl implements PerformanceModelFactory {

	private CommonModelFactory commonModelFactory;
	
	public PerformanceModelFactoryImpl(CommonModelFactory commonModelFactory){
		this.commonModelFactory = commonModelFactory;
	}

	@Override
	public PerformanceAdapter createAdapter(Context context) {
		return new PerformanceAdapter(context);
	}

	@Override
	public PerformanceController createController(Context context, PerformanceAdapter adapter, CurrentExerciseHolder holder, FragmentManagerProvider fragmentManagerProvider) {
		SetRetriever retriever = commonModelFactory.getSetRetriever();
		SetUpdater updater = commonModelFactory.getSetUpdater();
		ItemDeleter setDeleter = commonModelFactory.getSetDeleter();
		
		PerformanceBuilder builder = new PerformanceBuilder();
		EditSetDialogShower editDialog = new EditSetFragmentDialogShower(fragmentManagerProvider);
		
		return new PerformanceController(adapter, holder, retriever, builder, updater, editDialog, setDeleter);
	}

	@Override
	public SetLastResultUseCaseControllerImpl createSetLastResultUseCaseController(PerformanceActivity performanceActivity) {
		return new SetLastResultUseCaseControllerImpl(performanceActivity, commonModelFactory.getSetRetriever(), performanceActivity);
	}

	@Override
	public ContextualMenuHandlerForSets createContextHandler(PerformanceActivity activity) {
		return new ContextualMenuHandlerForSets(activity);
	}
	
}
