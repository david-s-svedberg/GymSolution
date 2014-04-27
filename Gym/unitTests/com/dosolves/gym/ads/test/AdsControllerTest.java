package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.view.Menu;

import com.dosolves.gym.ads.AdsController;
import com.dosolves.gym.ads.AdViewStateHandler;
import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.AdsShouldBeDisplayedDecider;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.ads.AdsUserGestureListener;
import com.dosolves.gym.ads.ViewSetter;
import com.dosolves.gym.app.SystemEventListener;

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
	AdViewStateHandler adsInitializerMock;
	@Mock
	AdsRemovalBuyer adsRemovalBuyerMock;
	
	AdsController sut;
	SystemEventListener sutAsSystemEventListener;
	AdsUserGestureListener sutAsUserGestureListener;
	
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
		sutAsSystemEventListener.onUIAboutToBeCreated();
		
		verify(deciderMock).adsShouldBeDisplayed();
	}
	
	@Test
	public void initzilizes_ads_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		sutAsSystemEventListener.onUIAboutToBeCreated();
		
		verify(adsInitializerMock).init();
	}

	
	@Test
	public void pauses_ads_when_gui_is_hidden_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		
		sutAsSystemEventListener.onUIHidden();
		
		verify(adsInitializerMock).pause();
	}
	
	@Test
	public void resumes_ads_when_gui_is_interactive_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		
		sutAsSystemEventListener.onUIInteractive();
		
		verify(adsInitializerMock).resume();
	}
	
	@Test
	public void destroys_ads_when_gui_is_destroyed_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		
		sutAsSystemEventListener.onUIDestroyed();
		
		verify(adsInitializerMock).destroy();
	}
	
	@Test
	public void setsAdsView_if_ads_should_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(true);
		sutAsSystemEventListener.onUIAboutToBeCreated();
		
		verify(viewSetterMock).setAdsView();
	}
	
	@Test
	public void setsNoAdsView_if_ads_should_not_be_displayed(){
		when(deciderMock.adsShouldBeDisplayed()).thenReturn(false);
		sutAsSystemEventListener.onUIAboutToBeCreated();
		
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
