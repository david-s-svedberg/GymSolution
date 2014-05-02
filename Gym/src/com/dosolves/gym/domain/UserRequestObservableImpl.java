package com.dosolves.gym.domain;

import java.util.ArrayList;
import java.util.List;

public class UserRequestObservableImpl implements UserRequestObservable {

	private List<UserRequestListener> userRequestListeners;
	
	public UserRequestObservableImpl(){
		userRequestListeners = new ArrayList<UserRequestListener>();
	}
	
	@Override
	public void registerUserRequestListener(UserRequestListener listener){
		userRequestListeners.add(listener);
	}
	
	@Override
	public void notifyDeleteItems(List<Integer> ids){
		for(UserRequestListener listener : userRequestListeners)
			listener.deleteItems(ids);
	}

	@Override
	public void notifyEditItem(Integer id) {
		for(UserRequestListener listener : userRequestListeners)
			listener.editItem(id);
	}
	
}
