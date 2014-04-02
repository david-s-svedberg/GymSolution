package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dosolves.gym.ads.AdsRemovalBoughtStorer;
import com.dosolves.gym.app.PreferenceRetriever;
import com.dosolves.gym.app.ads.PreferensesAdsRemovalBoughtStorer;

public class AdsRemovalBoughtStorerTest {
	
	@Mock
	PreferenceRetriever preferenceRetriverMock;
	@Mock
	SharedPreferences preferenceMock;
	@Mock
	Editor editorMock;
	
	AdsRemovalBoughtStorer sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PreferensesAdsRemovalBoughtStorer(preferenceRetriverMock);
	}
	
	@Test
	public void stores_value_correctly(){
		when(preferenceRetriverMock.getPreferences()).thenReturn(preferenceMock);
		when(preferenceMock.edit()).thenReturn(editorMock);
		
		sut.storeThatAdsRemovalHasBeenBought();
		
		verify(editorMock).putBoolean("showAds", false);
	}
}
