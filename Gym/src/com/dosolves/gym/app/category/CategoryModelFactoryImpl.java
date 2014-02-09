package com.dosolves.gym.app.category;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.category.database.CategoryDbStructureGiver;
import com.dosolves.gym.app.category.gui.ContextCategoryOpener;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.database.ExerciseDbStructureGiver;
import com.dosolves.gym.app.gui.CreateItemAlertDialogShower;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.category.data.CategoryUpdaterImpl;
import com.dosolves.gym.domain.category.data.CursorCategoryFactory;
import com.dosolves.gym.domain.category.data.CursorCategoryRetriever;

public class CategoryModelFactoryImpl implements CategoryModelFactory{

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
		
		return new CategoryController(adapter, retriever, createCategorydialog, updater, categoryOptionMenuDialog, categoryOpener,renameDialogShower);
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
		return new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), new CategoryDbStructureGiver(), new ExerciseDbStructureGiver());
	}

}
