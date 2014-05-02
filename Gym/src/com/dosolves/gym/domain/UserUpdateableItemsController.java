package com.dosolves.gym.domain;

import java.util.List;

import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;

public abstract class UserUpdateableItemsController implements SystemEventListener,
															   AddItemRequestedCallBack, 
															   ItemShouldBeCreatedCallback, 
															   ItemShouldBeRenamedCallback,
															   OpenItemRequestedCallback,
															   UserRequestListener{

	private CreateItemDialogShower createItemDialogShower;
	private RenameDialogShower renameDialogShower;
	private DeleteItemUseCaseController deleteItemUseCase;

	public UserUpdateableItemsController(CreateItemDialogShower createItemDialogShower, 
										 RenameDialogShower renameDialogShower, 
										 DeleteItemUseCaseController deleteItemUseCase) {
		this.createItemDialogShower = createItemDialogShower;
		this.renameDialogShower = renameDialogShower;
		this.deleteItemUseCase = deleteItemUseCase;
	}
	
	@Override
	public void onAddItemRequested() {
		createItemDialogShower.show(this);
	}
	
	@Override
	public void onItemShouldBeCreated(String newItemName) {
		handleItemShouldBeCreated(newItemName);
		handleUpdateItems();
	}
	
	@Override
	public void onItemShouldBeRenamed(int id, String newItemName) {
		handleItemShouldBeRenamed(id, newItemName);
		handleUpdateItems();
	}
	
	@Override
	public void onOpenItemRequested(int position) {
		handleItemShouldBeOpened(position);
	}
	
	@Override
	public void deleteItems(List<Integer> ids) {
		deleteItemUseCase.deleteItemsRequested(ids, new ItemsDeletedListener() {
			
			@Override
			public void onItemsHasBeenDeleted() {
				handleUpdateItems();		
			}
			
		});
		
	}
	
	@Override
	public void editItem(Integer id) {
		renameDialogShower.show(id, this, getItemCurrentName(id));
	}
	
	@Override
	public void onUIInteractive() {
		handleUpdateItems();
	}
	
	@Override
	public void onUIAboutToBeCreated() {}
	@Override
	public void onUICreated() {}
	@Override
	public void onMenuShouldBeCreated() {}
	@Override
	public void onUIHidden() {}
	@Override
	public void onUIDestroyed() {}
	
	protected abstract void handleUpdateItems();
	protected abstract void handleItemShouldBeOpened(int positionOfItemToBeOpened);
	protected abstract void handleItemShouldBeCreated(String newItemName);
	protected abstract void handleItemShouldBeRenamed(int id, String newName);
	
	protected abstract String getItemCurrentName(int id);
	
}
