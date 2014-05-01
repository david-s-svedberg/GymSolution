package com.dosolves.gym.domain;

import java.util.List;

import com.dosolves.gym.app.gui.AddItemRequestedCallBack;
import com.dosolves.gym.app.gui.OpenItemRequestedCallback;

public abstract class UserUpdateableItemsController implements ReadyToGetDataCallback,
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
	public void onReadyToGetData(){
		handleUpdateItems();
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
	public void renameItem(Integer id) {
		renameDialogShower.show(id, this, getItemCurrentName(id));
	}
	
	protected abstract void handleUpdateItems();
	protected abstract void handleItemShouldBeOpened(int positionOfItemToBeOpened);
	protected abstract void handleItemShouldBeCreated(String newItemName);
	protected abstract void handleItemShouldBeRenamed(int id, String newName);
	
	protected abstract String getItemCurrentName(int id);
	
}
