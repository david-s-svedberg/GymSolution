package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;

import com.dosolves.gym.ads.AdViewStateHandler;
import com.dosolves.gym.app.ads.AdViewStateHandlerImpl;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdsStateControllerTest {
	
	@Mock
	Activity activityMock;
	@Mock
	AdView adViewMock;
	
	AdViewStateHandler sut;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		sut = new AdViewStateHandlerImpl(activityMock);		 
	}
	
	@Test
	@Ignore("Can't mock final classes")
	public void setsup_ads_correctly(){
		when(activityMock.findViewById(anyInt())).thenReturn(adViewMock);
		sut.init();
		verify(adViewMock).loadAd(Matchers.<AdRequest>any());
	}

	
}
