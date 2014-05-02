package com.dosolves.gym.domain;

import java.util.List;

import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;

public class DeleteItemUseCaseControllerImpl implements DeleteItemUseCaseController {

	private ItemHasSubItemsChecker itemHasSubItemsChecker;
	private UserAsker userAsker;
	private ItemDeleter itemDeleter;
	
	public DeleteItemUseCaseControllerImpl(ItemHasSubItemsChecker itemHasSubItemsChecker, 
										   UserAsker userAsker, 
										   ItemDeleter itemDeleter) {
		this.itemHasSubItemsChecker = itemHasSubItemsChecker;
		this.userAsker = userAsker;
		this.itemDeleter = itemDeleter;
	}

	@Override
	public void deleteItemsRequested(final List<Integer> ids, final ItemsDeletedListener itemsDeletedListener) {
		if(anyItemHasChildren(ids)){
			userAsker.shouldParentItemBeDeleted(new AbstractUserResponseListener(){
				@Override
				public void yes() {
					deleteAllItems(ids);
					itemsDeletedListener.onItemsHasBeenDeleted();
				}
			});	
		}
		else{
			deleteAllItems(ids);
			itemsDeletedListener.onItemsHasBeenDeleted();
		}
	}

	private void deleteAllItems(List<Integer> ids) {
		for(int id:ids)
			itemDeleter.deleteItem(id);
	}

	private boolean anyItemHasChildren(List<Integer> ids) {
		for(int id:ids)
			if(itemHasSubItemsChecker.hasSubItems(id))
				return true;
		return false;
	}

}
