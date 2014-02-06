package com.dosolves.gym.domain.performance.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.dosolves.gym.app.performance.PerformanceModelFactoryImpl;
import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.performance.PerformanceModelFactory;

@RunWith(RobolectricTestRunner.class)
public class PerformanceModelFactoryImplTest {

	@Mock
	Context contextMock;
	@Mock
	PerformanceAdapter adapterMock;
	@Mock
	CurrentExerciseHolder holderMock;
	
	PerformanceModelFactory sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceModelFactoryImpl();
	}
	
	@Test
	public void creates_adapter_with_provided_context(){
		PerformanceAdapter adapter = sut.createAdapter(contextMock);
		assertSame(adapter.getContext(), contextMock);
	}
	
	@Test
	public void can_create_controller(){
		assertNotNull(sut.createController(contextMock, adapterMock, holderMock));		
	}
	
}
