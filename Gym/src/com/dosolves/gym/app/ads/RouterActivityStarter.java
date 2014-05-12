package com.dosolves.gym.app.ads;

import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;

public interface RouterActivityStarter {

	void startRouterActivity(RouteReason reason, RouteDialog module);

}
