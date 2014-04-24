package com.dosolves.gym.app.ads;

import com.dosolves.gym.app.ads.RouterActivity.RouteModule;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;

public interface RouterActivityStarter {

	void startRouterActivity(RouteReason reason, RouteModule module);

}
