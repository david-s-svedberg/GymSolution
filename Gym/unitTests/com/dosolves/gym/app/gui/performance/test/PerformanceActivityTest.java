package com.dosolves.gym.app.gui.performance.test;

import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.performance.NewSetShouldBeCreatedCallback;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;

@RunWith(RobolectricTestRunner.class)
public class PerformanceActivityTest {

	private static final double NEW_WEIGHT = 50.66;
	private static final int NEW_REPS = 12;

	PerformanceActivity sut;

	Set setMock;
	
	@Mock
	NewSetShouldBeCreatedCallback newSetShouldBeCreatedCallbackMock;
	@Mock
	SetShouldBeEditedCallback setShouldBeEditedCallbackMock;
	@Mock
	SystemEventListener systemEventListenerMock;
	
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		setMock = new Set(12,312,31,50, new Date());
		
		sut = new PerformanceActivity();
		sut.setNewSetShouldBeCreatedCallback(newSetShouldBeCreatedCallbackMock);
		sut.setSetShouldBeEditedCallback(setShouldBeEditedCallbackMock);
		
		sut.addSystemEventListener(systemEventListenerMock);
	}
	
	@Test
	public void onResume_calls_onUiInteractive(){
		sut.onResume();
		verify(systemEventListenerMock).onUIInteractive();
	}
	
	@Test
	public void passes_call_to_onSetShouldBeEdited_along(){
		sut.onSetShouldBeUpdated(setMock, NEW_REPS, NEW_WEIGHT);
		verify(setShouldBeEditedCallbackMock).onSetShouldBeUpdated(setMock, NEW_REPS, NEW_WEIGHT);
	}
	
}
