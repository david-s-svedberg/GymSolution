package com.dosolves.gym.domain;

import java.util.List;

public interface UserRequestObservable {

	public void registerUserRequestListener(UserRequestListener listener);

	public void notifyDeleteItems(List<Integer> ids);

	public void notifyEditItem(Integer id);

}