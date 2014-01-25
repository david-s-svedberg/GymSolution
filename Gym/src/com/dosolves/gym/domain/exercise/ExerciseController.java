package com.dosolves.gym.domain.exercise;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;

public class ExerciseController extends UserUpdateableItemsController {

	public ExerciseController(CreateItemDialogShower createItemDialogShower,
							  ItemOptionMenuDialogShower itemOptionMenuDialogShower) {
		super(createItemDialogShower, itemOptionMenuDialogShower);

	}

	@Override
	protected void handleUpdateItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleItemShouldBeOpened(int positionOfItemToBeOpened) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleItemShouldBeCreated(String newItemName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleItemShouldBeDeleted(int positionOfItemToBeDeleted) {
		// TODO Auto-generated method stub
		
	}

}
