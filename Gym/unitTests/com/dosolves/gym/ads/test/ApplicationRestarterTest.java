package com.dosolves.gym.ads.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.dosolves.gym.ads.ApplicationRestarter;
import com.dosolves.gym.app.ads.ContextApplicationRestarter;

@RunWith(RobolectricTestRunner.class)
public class ApplicationRestarterTest {

	@Mock
	Context contextMock;
	
	ApplicationRestarter sut;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ContextApplicationRestarter(contextMock);
	}
	
	@Test
	@Ignore("Doesn't play nice")
	public void uses_provided_context_to_show_toast(){
		sut.restart();
	}
	
}
