package com.dosolves.gym.domain.category;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;

public class CategoryController extends UserUpdateableItemsController {

	private ArrayAdapter<Category> adapter;
	private CategoryRetriever retriever;
	private CategoryUpdater updater;
	private CategoryOpener categoryOpener;
	
	public CategoryController(ArrayAdapter<Category> adapter, 
							  CategoryRetriever retriever, 
							  CreateItemDialogShower createItemDialogShower, 
							  CategoryUpdater categoryUpdater, 
							  ItemOptionMenuDialogShower itemOptionMenuDialogShower, 
							  CategoryOpener categoryOpener,
							  RenameDialogShower renameDialogShower, 
							  DeleteItemUseCaseController categoryDeleteUseCase) {
		super(createItemDialogShower, itemOptionMenuDialogShower, renameDialogShower, categoryDeleteUseCase);
		this.adapter = adapter;
		this.retriever = retriever;
		this.updater = categoryUpdater;
		this.categoryOpener = categoryOpener;			
	}

	@Override
	protected void handleUpdateItems() {
		adapter.clear();
		adapter.addAll(retriever.getCategories());
		adapter.notifyDataSetChanged();		
	}

	@Override
	protected void handleItemShouldBeOpened(int positionOfItemToBeOpened) {
		categoryOpener.openCategory(adapter.getItem(positionOfItemToBeOpened));
	}

	@Override
	protected void handleItemShouldBeCreated(String newItemName) {
		if(!categoryWithSameNameExists(newItemName)){
			updater.create(newItemName);			
		}
		
	}
	
	private boolean categoryWithSameNameExists(String newCategoryName) {
		for(Category current: retriever.getCategories())
			if (current.getName().equalsIgnoreCase(newCategoryName))
				return true;
		
		return false;
	}

	@Override
	protected void handleItemShouldBeRenamed(int positionOfItemToBeRenamed, String newName) {
		updater.rename(adapter.getItem(positionOfItemToBeRenamed), newName);
	}

	@Override
	protected String getItemCurrentName(int positionOfItem) {
		return adapter.getItem(positionOfItem).getName();
	}

	@Override
	protected int getItemId(int positionOfItem) {
		return adapter.getItem(positionOfItem).getId();
	}
	
}
