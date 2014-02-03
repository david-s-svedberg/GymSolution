package com.dosolves.gym.app.gui.performance.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.exercise.Exercise;

@RunWith(RobolectricTestRunner.class)
public class PerformanceAdapterTest {

	@Mock
	Context contextMock;
	
	PerformanceAdapter sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceAdapter(contextMock);
	}
	
	@Test
	public void getView_returns_layout(){
		assertNotNull(sut.getView(0, null, null));		
	}
	
}

