package com.dosolves.gym.domain;

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
	public void deleteItemRequested(final int id) {
		if(itemHasSubItemsChecker.hasSubItems(id)){
			userAsker.shouldParentItemBeDeleted(new AbstractUserResponseListener(){
				@Override
				public void yes() {
					itemDeleter.deleteItem(id);
				}
			});	
		}
		else{
			itemDeleter.deleteItem(id);
		}
	}

}
