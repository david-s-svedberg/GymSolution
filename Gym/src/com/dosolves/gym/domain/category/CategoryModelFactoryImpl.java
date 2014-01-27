package com.dosolves.gym.domain.category;

import com.dosolves.gym.R;
import com.dosolves.gym.app.database.SQLiteDataAccess;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.database.category.CategoryDbStructureGiver;
import com.dosolves.gym.app.database.category.CursorCategoryFactory;
import com.dosolves.gym.app.database.category.CursorCategoryRetriever;
import com.dosolves.gym.app.gui.ItemOptionMenuAlertDialogShower;
import com.dosolves.gym.app.gui.category.ContextCategoryOpener;
import com.dosolves.gym.app.gui.category.CreateItemAlertDialogShower;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CategoryModelFactoryImpl implements CategoryModelFactory{

	@Override
	public ArrayAdapter<Category> createAdapter(Context context) {
		return new ArrayAdapter<Category>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public CategoryController createController(Context context, ArrayAdapter<Category> adapter) {
		DbStructureGiver categoryStructure = new CategoryDbStructureGiver();
		DataAccess dao = new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), categoryStructure);
		CursorCategoryFactory categoryFactory = new CursorCategoryFactory(categoryStructure);
		CategoryRetriever retriever = new CursorCategoryRetriever(dao, categoryFactory);
		CategoryUpdater updater = new CategoryUpdaterImpl(dao);
		CreateItemDialogShower createCategorydialog = new CreateItemAlertDialogShower(context, context.getString(R.string.create_category));
		ItemOptionMenuDialogShower categoryOptionMenuDialog = new ItemOptionMenuAlertDialogShower(context);
		ContextCategoryOpener categoryOpener = new ContextCategoryOpener(context);
		
		return new CategoryController(adapter, retriever, createCategorydialog, updater, categoryOptionMenuDialog, categoryOpener);
	}

}
