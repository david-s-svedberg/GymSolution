package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.ads.AdsRemovalBoughtStorer;
import com.dosolves.gym.ads.ApplicationRestarter;
import com.dosolves.gym.ads.UserThanker;
import com.dosolves.gym.app.ads.AdsRemovalBoughtController;

public class AdsRemovalBoughtControllerTest {

	@Mock
	AdsRemovalBoughtStorer adsRemovalBoughtStorerMock;
	@Mock
	UserThanker userThankerMock;
	@Mock
	ApplicationRestarter applicationRestarterMock;
	
	AdsRemovalBoughtController sut;
	

	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new AdsRemovalBoughtController(adsRemovalBoughtStorerMock, userThankerMock, applicationRestarterMock);
	}
	
	@Test
	public void sets_that_adremoval_has_been_bought(){
		sut.onAdsRemovalPurchased();
		
		verify(adsRemovalBoughtStorerMock).storeThatAdsRemovalHasBeenBought();
	}
	
	@Test
	public void thanks_user_for_payment(){
		sut.onAdsRemovalPurchased();
		
		verify(userThankerMock).thankUser();
	}
	
	@Test
	public void restarts_application(){
		sut.onAdsRemovalPurchased();
		
		verify(applicationRestarterMock).restart();
	}
	
}
