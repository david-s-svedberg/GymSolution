package com.dosolves.gym.domain;

import com.dosolves.gym.app.ads.RouterActivityCreatedListener;

public interface UserAsker extends RouterActivityCreatedListener{

	void askUser(UserResponseListener responseListener);
	UserResponseListener getCurrentResponseListener();
	
}
