package com.dosolves.gym.domain;

public interface UserAsker {

	void shouldParentItemBeDeleted(UserResponseListener responseListener);
	UserResponseListener getCurrentResponseListener();
	
}
