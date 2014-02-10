package com.dosolves.gym.app.gui.performance.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.ReadyToGetDataCallback;
import com.dosolves.gym.domain.performance.NewSetShouldBeCreatedCallback;

@RunWith(RobolectricTestRunner.class)
public class PerformanceActivityTest {

	@Mock
	NewSetShouldBeCreatedCallback newSetShouldBeCreatedCallbackMock;
	@Mock
	ReadyToGetDataCallback readyToGetDataCallbackMock;
	
	PerformanceActivity sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceActivity();
		sut.setNewSetShouldBeCreatedCallback(newSetShouldBeCreatedCallbackMock);
		sut.setReadyToGetDataCallback(readyToGetDataCallbackMock);
	}
	
	@Test
	public void onResume_calls_onReadyToGetData(){
		sut.onResume();
		verify(readyToGetDataCallbackMock).onReadyToGetData();
	}
	
}
