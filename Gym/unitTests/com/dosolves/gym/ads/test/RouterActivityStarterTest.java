package com.dosolves.gym.ads.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.app.ContextRouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;

@RunWith(RobolectricTestRunner.class)
public class RouterActivityStarterTest {

	@Mock
	Context contextMock;
	
	RouterActivityStarter sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ContextRouterActivityStarter(contextMock);
	}
	
	@Test
	public void calls_startActivity_when_startRouterActivity_is_called(){
		
		sut.startRouterActivity(RouteReason.FOR_IN_APP_BILLING, RouteDialog.NONE);
		
		verify(contextMock).startActivity(argThat(new ArgumentMatcher<Intent>(){

			@Override
			public boolean matches(Object argument) {
				Intent intent = (Intent) argument;
				if (intentTargetsRouterActivity(intent))
					return true;
				else
					return false;
			}

			private boolean intentTargetsRouterActivity(Intent intent) {
				assertTrue(intent.getComponent().getClassName().equals(RouterActivity.class.getName()));
				return true;
			}
			
		}));
	}

}
