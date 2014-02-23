package com.dosolves.gym.domain;

import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;
import com.dosolves.gym.app.gui.RenameDialogRequestedCallback;

public abstract class UserUpdateableItemsController implements ReadyToGetDataCallback,
															   AddItemRequestedCallBack, 
															   ItemMenuRequestedCallback,
															   RenameDialogRequestedCallback,
															   ItemShouldBeCreatedCallback, 
															   ItemShouldBeDeletedCallback,
															   ItemShouldBeRenamedCallback,
															   OpenItemRequestedCallback{

	private CreateItemDialogShower createItemDialogShower;
	private ItemOptionMenuDialogShower itemOptionMenuDialogShower;
	private RenameDialogShower renameDialogShower;

	public UserUpdateableItemsController(CreateItemDialogShower createItemDialogShower, ItemOptionMenuDialogShower itemOptionMenuDialogShower, RenameDialogShower renameDialogShower) {
		this.createItemDialogShower = createItemDialogShower;
		this.itemOptionMenuDialogShower = itemOptionMenuDialogShower;
		this.renameDialogShower = renameDialogShower;
	}
	
	@Override
	public void onAddItemRequested() {
		createItemDialogShower.show(this);
	}
	
	@Override
	public void onItemMenuRequested(int itemPosition) {
		itemOptionMenuDialogShower.show(itemPosition, this, this);
	}
	
	@Override
	public void onRenameDialogRequested(int itemPosition) {
		renameDialogShower.show(itemPosition, this, getItemCurrentName(itemPosition));
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
	public void onItemShouldBeRenamed(int itemPosition, String newItemName) {
		handleItemShouldBeRenamed(itemPosition, newItemName);
		handleUpdateItems();
	}
	
	@Override
	public void onOpenItemRequested(int position) {
		handleItemShouldBeOpened(position);
	}
	
	@Override
	public void onReadyToGetData(){
		handleUpdateItems();
	}
	
	protected abstract void handleUpdateItems();
	protected abstract void handleItemShouldBeOpened(int positionOfItemToBeOpened);
	protected abstract void handleItemShouldBeCreated(String newItemName);
	protected abstract void handleItemShouldBeDeleted(int positionOfItemToBeDeleted);
	protected abstract void handleItemShouldBeRenamed(int positionOfItemToBeRenamed, String newName);
	protected abstract String getItemCurrentName(int positionOfItem);
	
}
