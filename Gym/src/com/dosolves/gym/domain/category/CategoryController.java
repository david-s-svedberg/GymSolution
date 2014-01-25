package com.dosolves.gym.domain.category;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;

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
							  CategoryOpener categoryOpener) {
		super(createItemDialogShower, itemOptionMenuDialogShower);
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
	
	@Override
	protected void handleItemShouldBeDeleted(int positionOfItemToBeDeleted) {
		updater.delete(adapter.getItem(positionOfItemToBeDeleted));        
	}
	
	private boolean categoryWithSameNameExists(String newCategoryName) {
		for(Category current: retriever.getCategories())
			if (current.getName().equalsIgnoreCase(newCategoryName))
				return true;
		
		return false;
	}
	
}
