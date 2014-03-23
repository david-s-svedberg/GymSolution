package com.dosolves.gym.app.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.SharedPreferences;

import com.dosolves.gym.app.PreferenceAdsShouldBeDisplayedDecider;
import com.dosolves.gym.app.PreferenceRetriever;

public class PreferenceAdsShouldBeDisplayedCheckerTest {

	@Mock
	PreferenceRetriever retrieverMock;
	@Mock
	SharedPreferences preferencesMock;
	
	PreferenceAdsShouldBeDisplayedDecider sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PreferenceAdsShouldBeDisplayedDecider(retrieverMock);
	}
	
	@Test
	public void retrieves_preferences_when_asked_if_ads_should_be_displayed(){
		when(retrieverMock.getPreferences()).thenReturn(preferencesMock);
		sut.adsShouldBeDisplayed();
		verify(retrieverMock).getPreferences();
	}
	
	@Test
	public void returns_true_if_no_setting_is_present(){
		when(retrieverMock.getPreferences()).thenReturn(preferencesMock);
		sut.adsShouldBeDisplayed();
		verify(preferencesMock).getBoolean(anyString(), eq(true));
	}
	
	@Test
	public void returns_true_if_setting_showAds_is_true(){
		when(retrieverMock.getPreferences()).thenReturn(preferencesMock);
		when(preferencesMock.getBoolean(eq("showAds"), anyBoolean())).thenReturn(true);
		assertTrue(sut.adsShouldBeDisplayed());
	}
	
	@Test
	public void returns_false_if_setting_showAds_is_false(){
		when(retrieverMock.getPreferences()).thenReturn(preferencesMock);
		when(preferencesMock.getBoolean(eq("showAds"), anyBoolean())).thenReturn(false);
		assertFalse(sut.adsShouldBeDisplayed());
	}
		
}
