package com.dosolves.gym.domain;

import java.util.List;

public interface DeleteItemUseCaseController {

	void deleteItemsRequested(List<Integer> ids, ItemsDeletedListener itemsDeletedListener);

}
