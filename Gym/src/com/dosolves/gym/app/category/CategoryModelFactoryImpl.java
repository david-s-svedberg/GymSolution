package com.dosolves.gym.app.category;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dosolves.gym.R;
import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.ads.ContextRouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.category.gui.ContextCategoryOpener;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.app.gui.YesNoDialog;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryController;
import com.dosolves.gym.domain.category.CategoryModelFactory;
import com.dosolves.gym.domain.category.data.CategoryItemHasSubItemsChecker;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.domain.data.DataAccess;

public class CategoryModelFactoryImpl implements CategoryModelFactory{

	private UserAskerImpl userAsker;
	private CommonModelFactory commonModelFactory;

	public CategoryModelFactoryImpl(CommonModelFactory commonModelFactory) {
		this.commonModelFactory = commonModelFactory;
	}

	@Override
	public ArrayAdapter<Category> createAdapter(Context context) {
		return new ArrayAdapter<Category>(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public CategoryController createController(Context context, ArrayAdapter<Category> adapter) {
		CategoryRetriever retriever = commonModelFactory.getCategoryRetriever();
		CategoryUpdater updater = commonModelFactory.getCategoryUpdater();
		
		CreateItemDialogShower createCategorydialog = commonModelFactory.createCreateItemDialogShower(context, context.getString(R.string.create_category));
		RenameDialogShower renameDialogShower = commonModelFactory.createRenameDialogShower(context, context.getString(R.string.rename_category));
		
		ContextCategoryOpener categoryOpener = new ContextCategoryOpener(context);
		DeleteItemUseCaseController deleteItemUseCase = createDeleteUseCase(context, updater);
		
		return new CategoryController(adapter, retriever, createCategorydialog, updater, categoryOpener,renameDialogShower, deleteItemUseCase);
	}
	
	private DeleteItemUseCaseController createDeleteUseCase(Context context, CategoryUpdater categoryUpdater) {
		ItemDeleter categoryDeleter = commonModelFactory.getCategoryDeleter();
		UserAskerImpl userAsker = createUserAsker(context);
		DataAccess dataAccess = commonModelFactory.getDataAccess();
		CategoryItemHasSubItemsChecker categoryItemHasSubItemsChecker = new CategoryItemHasSubItemsChecker(dataAccess);
		DeleteItemUseCaseController deleteItemUseCase = new DeleteItemUseCaseControllerImpl(categoryItemHasSubItemsChecker, userAsker, categoryDeleter);
		
		return deleteItemUseCase;
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
