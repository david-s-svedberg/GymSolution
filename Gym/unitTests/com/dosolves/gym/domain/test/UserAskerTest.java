package com.dosolves.gym.domain.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;

import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.app.ads.RouterActivityCreatedListener;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.RouterActivity.RouteDialog;
import com.dosolves.gym.app.gui.UserAskerImpl;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserResponseListener;

@RunWith(RobolectricTestRunner.class)
public class UserAskerTest {
	
	@Mock
	RouterActivityStarter routerStarterMock;
	@Mock
	Activity activityMock;
	@Mock
	DialogFragment dialogMock;
	@Mock
	FragmentManager fragmentManagerMock;
	@Mock
	UserResponseListener responseListenerMock;
	
	UserAsker sut;
	RouterActivityCreatedListener sutAsRouterActivityCreatedListener;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		UserAskerImpl sutImpl = new UserAskerImpl(routerStarterMock,dialogMock, RouteReason.FOR_DIALOG,RouteDialog.DELETE_CATEGORY);
		
		sut = sutImpl;
		sutAsRouterActivityCreatedListener =sutImpl; 
	}
	
	@Test
	public void starts_router_activity(){
		sut.askUser(responseListenerMock);
		
		verify(routerStarterMock).startRouterActivity(RouterActivity.RouteReason.FOR_DIALOG, RouteDialog.DELETE_CATEGORY);
	}
	
	@Test
	public void shows_dialog_when_router_activity_has_been_created(){
		when(activityMock.getFragmentManager()).thenReturn(fragmentManagerMock);
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(dialogMock).show(eq(fragmentManagerMock), anyString());
	}
	
	@Test
	public void exposes_current_responselistener(){
		sut.askUser(responseListenerMock);
		assertEquals(responseListenerMock, sut.getCurrentResponseListener());
	}
	
}
