package com.dosolves.gym.ads.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.dosolves.gym.ads.UserThanker;
import com.dosolves.gym.app.ads.ToastUserThanker;

@RunWith(RobolectricTestRunner.class)
public class UserThankerTest {

	@Mock
	Context contextMock;
	
	UserThanker sut;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ToastUserThanker(contextMock);
	}
	
	@Test
	@Ignore("Roboelectric can't use mock context")
	public void uses_provided_context_to_show_toast(){
		sut.thankUser();
	}
}
