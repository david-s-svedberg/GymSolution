package com.dosolves.gym.domain;

import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;

public abstract class UserUpdateableItemsController implements AddItemRequestedCallBack, ItemMenuRequestedCallback, ItemShouldBeCreatedCallback, ItemShouldBeDeletedCallback, OpenItemRequestedCallback{

	private CreateItemDialogShower createItemDialogShower;
	private ItemOptionMenuDialogShower itemOptionMenuDialogShower;

	public UserUpdateableItemsController(CreateItemDialogShower createItemDialogShower, ItemOptionMenuDialogShower itemOptionMenuDialogShower) {
		this.createItemDialogShower = createItemDialogShower;
		this.itemOptionMenuDialogShower = itemOptionMenuDialogShower;
	}
	
	public void init() {
		handleUpdateItems();
	}
	
	@Override
	public void onAddItemRequested() {
		createItemDialogShower.show(this);
	}
	
	@Override
	public void onItemMenuRequested(int itemPosition) {
		itemOptionMenuDialogShower.show(itemPosition, this);
	}

	@Override
	public void onItemShouldBeCreated(String newItemName) {
		handleItemShouldBeCreated(newItemName);
		handleUpdateItems();
	}
	
	@Override
	public void onItemShouldBeDeleted(int itemPosition) {
		handleItemShouldBeDeleted(itemPosition);
		handleUpdateItems();
	}
	
	@Override
	public void onOpenItemRequested(int position) {
		handleItemShouldBeOpened(position);
	}
	
	protected abstract void handleUpdateItems();
	protected abstract void handleItemShouldBeOpened(int positionOfItemToBeOpened);
	protected abstract void handleItemShouldBeCreated(String newItemName);
	protected abstract void handleItemShouldBeDeleted(int positionOfItemToBeDeleted);
}
