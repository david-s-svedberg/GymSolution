package com.dosolves.gym.domain.exercise;

import java.util.List;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;

public class ExerciseController extends UserUpdateableItemsController {

	private ArrayAdapter<Exercise> adapter;
	private ExerciseRetriever retriever;
	private ExerciseUpdater updater;
	private ExerciseOpener opener;
	private CurrentCategoryHolder currentCategoryHolder;

	public ExerciseController(ArrayAdapter<Exercise> adapter, 
							  ExerciseRetriever retriever, 
							  CreateItemDialogShower createItemDialogShower,
							  ExerciseUpdater updater, 
							  ExerciseOpener opener, 
							  CurrentCategoryHolder currentCategoryHolder,
							  RenameDialogShower renameDialogShower,
							  DeleteItemUseCaseController deleteItemUseCase) {
		super(createItemDialogShower, renameDialogShower,deleteItemUseCase);
		this.adapter = adapter;
		this.retriever = retriever;
		this.updater = updater;
		this.opener = opener;
		this.currentCategoryHolder = currentCategoryHolder;

	}

	@Override
	protected void handleUpdateItems() {
		adapter.clear();
		adapter.addAll(getExercisesForCurrentCategory());
		adapter.notifyDataSetChanged();		
	}

	private List<Exercise> getExercisesForCurrentCategory() {
		Category currentCategory = currentCategoryHolder.getCurrentCategory();
		return retriever.getExercisesInCategory(currentCategory.getId());
	}

	@Override
	protected void handleItemShouldBeOpened(int positionOfItemToBeOpened) {
		opener.openExercise(adapter.getItem(positionOfItemToBeOpened));
	}

	@Override
	protected void handleItemShouldBeCreated(String newItemName) {
		if(!exerciseWithSameNameExistsInCategory(newItemName)){
			updater.create(newItemName, currentCategoryHolder.getCurrentCategory().getId());			
		}
		
	}

	private boolean exerciseWithSameNameExistsInCategory(String newItemName) {
		for(Exercise current: getExercisesForCurrentCategory())
			if (current.getName().equals(newItemName))
				return true;
		return false;
	}
	
	@Override
	protected void handleItemShouldBeRenamed(int id, String newName) {
		updater.rename(id, newName);
	}

	@Override
	protected String getItemCurrentName(int id) {
		return retriever.getExercise(id).getName();
	}

}
