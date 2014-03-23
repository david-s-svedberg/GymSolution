package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;

import com.dosolves.gym.ads.AdsInitializer;
import com.dosolves.gym.app.gui.AdsInitializerImpl;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdsInitializerTest {
	
	@Mock
	Activity activityMock;
	@Mock
	AdView adViewMock;
	
	AdsInitializer sut;

	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		sut = new AdsInitializerImpl(activityMock);		 
	}
	
	@Test
	@Ignore("Can't mock final classes")
	public void setsup_ads_correctly(){
		when(activityMock.findViewById(anyInt())).thenReturn(adViewMock);
		sut.init();
		verify(adViewMock).loadAd(Matchers.<AdRequest>any());
	}

	
}
