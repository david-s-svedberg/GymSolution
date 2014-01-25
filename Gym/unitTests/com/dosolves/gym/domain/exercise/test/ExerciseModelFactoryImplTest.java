package com.dosolves.gym.domain.exercise.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseModelFactory;
import com.dosolves.gym.domain.exercise.ExerciseModelFactoryImpl;

@RunWith(RobolectricTestRunner.class)
public class ExerciseModelFactoryImplTest {
	
	@Mock
	Context contextMock;
	@Mock
	ArrayAdapter<Exercise> adapterMock;
	
	ExerciseModelFactory sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ExerciseModelFactoryImpl();
	}
	
	@Test
	public void creates_adapter_with_provided_context(){
		ArrayAdapter<Exercise> adapter = sut.createAdapter(contextMock);
		assertSame(adapter.getContext(), contextMock);
	}
	
	@Test
	public void can_create_controller(){
		assertNotNull(sut.createController(contextMock, adapterMock));		
	}
}
