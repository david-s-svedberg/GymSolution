package com.dosolves.gym.domain;

import java.util.List;

public interface UserRequestListener {

	void deleteItems(List<Integer> ids);
	void editItem(Integer id);

}
