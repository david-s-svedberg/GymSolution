package com.dosolves.gym.app.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dosolves.gym.app.FirstTimeAppStartDecider;
import com.dosolves.gym.app.FirstTimeAppStartDeciderImpl;
import com.dosolves.gym.app.PreferenceRetriever;
import com.dosolves.gym.app.database.DataBaseEmptyChecker;

public class FirstTimeAppStartDeciderTest {

	FirstTimeAppStartDecider sut;
	
	@Mock
	PreferenceRetriever pereferenceRetrieverMock;
	@Mock
	SharedPreferences preferenceMock;
	@Mock
	DataBaseEmptyChecker dataBaseEmptyCheckerMock;
	@Mock
	Editor editorMock;

	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	
		sut = new FirstTimeAppStartDeciderImpl(pereferenceRetrieverMock, dataBaseEmptyCheckerMock);		
	}
	
	@Test
	public void checks_if_preference_is_set() {

		when(pereferenceRetrieverMock.getPreferences()).thenReturn(preferenceMock);
		
		sut.appIsRunningForTheFirstTime();
		
		verify(preferenceMock).getBoolean("firstRun", true);
	}
	
	@Test
	public void returns_false_if_preference_is_sat_to_false() {
		when(pereferenceRetrieverMock.getPreferences()).thenReturn(preferenceMock);
		when(preferenceMock.getBoolean(anyString(), anyBoolean())).thenReturn(false);		
		
		assertFalse(sut.appIsRunningForTheFirstTime());
	}
	
	@Test
	public void checks_if_database_is_empty_if_preference_returns_true() {
		when(pereferenceRetrieverMock.getPreferences()).thenReturn(preferenceMock);
		when(preferenceMock.getBoolean(anyString(), anyBoolean())).thenReturn(true);		
		when(preferenceMock.edit()).thenReturn(editorMock);
		
		sut.appIsRunningForTheFirstTime();
		
		verify(dataBaseEmptyCheckerMock).isDbEmpty();
	}
	
	@Test
	public void doesnt_check_if_database_is_empty_if_preference_returns_false() {
		when(pereferenceRetrieverMock.getPreferences()).thenReturn(preferenceMock);
		when(preferenceMock.getBoolean(anyString(), anyBoolean())).thenReturn(false);		
		
		sut.appIsRunningForTheFirstTime();
		
		verifyZeroInteractions(dataBaseEmptyCheckerMock);
	}
	
	@Test
	public void sets_preference_value_to_true_if_it_was_the_first_time() {
		when(pereferenceRetrieverMock.getPreferences()).thenReturn(preferenceMock);
		when(preferenceMock.getBoolean(anyString(), anyBoolean())).thenReturn(true);		
		when(dataBaseEmptyCheckerMock.isDbEmpty()).thenReturn(true);
		when(preferenceMock.edit()).thenReturn(editorMock);
		
		sut.appIsRunningForTheFirstTime();

		verify(editorMock).putBoolean("firstRun", false);
		verify(editorMock).commit();
	}

}
