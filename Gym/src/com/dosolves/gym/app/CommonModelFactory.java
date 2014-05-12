package com.dosolves.gym.app;

import android.content.Context;

import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.gui.ContextualMenuHandler;
import com.dosolves.gym.app.gui.UserUpdateableItemsActivity;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;

public interface CommonModelFactory {
	
	DataAccess getDataAccess();
	
	SetRetriever getSetRetriever();
	SetUpdater getSetUpdater();
	
	ExerciseRetriever getExerciseRetriever();
	ExerciseUpdater getExerciseUpdater();

	CategoryRetriever getCategoryRetriever();
	CategoryUpdater getCategoryUpdater();
	
	CreateItemDialogShower createCreateItemDialogShower(Context context, String title);
	RenameDialogShower createRenameDialogShower(Context context, String title);
	
	ItemDeleter getExerciseDeleter();
	ItemDeleter getCategoryDeleter();
	ItemDeleter getSetDeleter();
	
	ContextualMenuHandler createContextualMenuHandler(UserUpdateableItemsActivity activity);

	PreferenceRetriever getpreferenceRetriever(Context context);
	RouterActivityStarter getRouterActivityStarter(Context context);
	
}
