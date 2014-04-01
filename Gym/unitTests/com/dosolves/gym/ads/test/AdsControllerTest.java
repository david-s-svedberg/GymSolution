package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.view.Menu;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.ads.AdsInitializer;
import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.ads.SystemEventListener;
import com.dosolves.gym.ads.UserGestureListener;
import com.dosolves.gym.ads.ViewSetter;
import com.dosolves.gym.domain.AdsShouldBeDisplayedDecider;

public class AdsControllerTest {

	@Mock
	AdsShouldBeDisplayedDecider deciderMock;
	@Mock
	ViewSetter viewSetterMock;
	@Mock
	MenuSetter menuSetterMock;
	@Mock
	Menu menuMock;
	@Mock
	AdsInitializer adsInitializerMock;
	@Mock
	AdsRemovalBuyer adsRemovalBuyerMock;
	
	AdsController sut;
	SystemEventListener sutAsSystemEventListener;
	UserGestureListener sutAsUserGestureListener;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		AdsController impl = new AdsController(deciderMock, viewSetterMock, menuSetterMock, adsInitializerMock, adsRemovalBuyerMock);
		sut = impl;
		sutAsSystemEventListener = impl;
		sutAsUserGestureListener = impl;
	}
	
	@Test
	public void calls_decider_when_gui_is_about_to_be_shown(){
		sutAsSystemEventListener.onUIAboutToBeShown();
		
		verify(deciderMock).adsShouldBeDisplayed();
	}
	
	@Test
	public void initzilizes_apps_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		sutAsSystemEventListener.onUIAboutToBeShown();
		
		verify(adsInitializerMock).init();
	}
	
	@Test
	public void setsAdsView_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		sutAsSystemEventListener.onUIAboutToBeShown();
		
		verify(viewSetterMock).setAdsView();
	}
	
	@Test
	public void setsNoAdsView_if_ads_should_not_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(false);
		sutAsSystemEventListener.onUIAboutToBeShown();
		
		verify(viewSetterMock).setAdsFreeView();
	}
	
	@Test
	public void sets_ads_menu_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		
		sutAsSystemEventListener.onMenuShouldBeCreated();
		
		verify(menuSetterMock).setAdsMenu();
	}
	
	@Test
	public void sets_normal_menu_if_ads_should_not_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(false);
		
		sutAsSystemEventListener.onMenuShouldBeCreated();
		
		verify(menuSetterMock).setAdsFreeMenu();
	}
	
	@Test
	public void calls_AdsRemovalBuyer_when_requested(){
		sutAsUserGestureListener.onPurchaseAdsRemovalRequested();
		
		verify(adsRemovalBuyerMock).buyAdsRemoval();
	}
}
