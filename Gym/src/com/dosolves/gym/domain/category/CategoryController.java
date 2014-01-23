package com.dosolves.gym.domain.category;

import com.dosolves.gym.domain.ItemMenuRequestedCallback;

import android.widget.ArrayAdapter;

public class CategoryController implements CategoryShouldBeCreatedCallback, 
										   AddCategoryRequestedCallBack,
										   ItemMenuRequestedCallback,
										   CategoryShouldBeDeletedCallback,
										   CategoryClickedCallback{

	private ArrayAdapter<Category> adapter;
	private CategoryRetriever retriever;
	private CreateCategoryDialog createCategorydialog;
	private CategoryUpdater updater;
	private CategoryOptionMenuDialog categoryOptionMenuDialog;
	private CategoryOpener categoryOpener;

	public CategoryController(ArrayAdapter<Category> adapter, 
							  CategoryRetriever retriever, 
							  CreateCategoryDialog createCategorydialog, 
							  CategoryUpdater categoryUpdater, 
							  CategoryOptionMenuDialog categoryOptionMenuDialog, 
							  CategoryOpener categoryOpener) {
		this.adapter = adapter;
		this.retriever = retriever;
		this.createCategorydialog = createCategorydialog;
		this.updater = categoryUpdater;
		this.categoryOptionMenuDialog = categoryOptionMenuDialog;
		this.categoryOpener = categoryOpener;		
	}

	public void init() {
		updateCategories();
	}

	private void updateCategories() {
		adapter.clear();
		adapter.addAll(retriever.getCategories());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onAddCategoryRequested() {
		createCategorydialog.show(this);
	}

	@Override
	public void onCategoryShouldBeCreated(String newCategoryName) {
		if(!categoryWithSameNameExists(newCategoryName)){
			updater.create(newCategoryName);
			updateCategories();	
		}		
	}

	private boolean categoryWithSameNameExists(String newCategoryName) {
		for(Category current: retriever.getCategories())
			if (current.getName().equalsIgnoreCase(newCategoryName))
				return true;
		
		return false;
	}

	@Override
	public void onItemMenuRequested(int itemPosition) {
		categoryOptionMenuDialog.show(adapter.getItem(itemPosition), this);
	}

	@Override
	public void onCategoryShouldBeDeleted(Category categoryToBeDeleted) {
		updater.delete(categoryToBeDeleted);
		updateCategories();
	}

	@Override
	public void onCategoryClicked(int position) {
		categoryOpener.openCategory(adapter.getItem(position));
	}

}
