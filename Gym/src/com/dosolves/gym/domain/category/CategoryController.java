package com.dosolves.gym.domain.category;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;
import com.dosolves.gym.domain.category.data.CategoryRetriever;
import com.dosolves.gym.domain.category.data.CategoryUpdater;
import com.dosolves.gym.easteregg.EasterEggUseCase;

public class CategoryController extends UserUpdateableItemsController {

	private ArrayAdapter<Category> adapter;
	private CategoryRetriever retriever;
	private CategoryUpdater updater;
	private CategoryOpener categoryOpener;
	private EasterEggUseCase easterEggUseCase;
	
	public CategoryController(ArrayAdapter<Category> adapter, 
							  CategoryRetriever retriever, 
							  CreateItemDialogShower createItemDialogShower, 
							  CategoryUpdater categoryUpdater, 
							  CategoryOpener categoryOpener,
							  RenameDialogShower renameDialogShower, 
							  DeleteItemUseCaseController categoryDeleteUseCase, 
							  EasterEggUseCase easterEggUseCase) {
		super(createItemDialogShower, renameDialogShower, categoryDeleteUseCase);
		this.adapter = adapter;
		this.retriever = retriever;
		this.updater = categoryUpdater;
		this.categoryOpener = categoryOpener;
		this.easterEggUseCase = easterEggUseCase;			
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
		if(!easterEggUseCase.triggersOn(newItemName)){
			if(!categoryWithSameNameExists(newItemName)){
				updater.create(newItemName);			
			}
		}
	}
	
	private boolean categoryWithSameNameExists(String newCategoryName) {
		for(Category current: retriever.getCategories())
			if (current.getName().equalsIgnoreCase(newCategoryName))
				return true;
		
		return false;
	}

	@Override
	protected void handleItemShouldBeRenamed(int id, String newName) {
		updater.rename(id, newName);
	}

	@Override
	protected String getItemCurrentName(int id) {
		return retriever.getCategory(id).getName();
	}
	
}
