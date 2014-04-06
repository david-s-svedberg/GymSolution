package com.dosolves.gym.domain.performance.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseController;
import com.dosolves.gym.domain.performance.SetLastResultUseCaseControllerImpl;
import com.dosolves.gym.domain.performance.StartValueSetter;
import com.dosolves.gym.domain.performance.data.SetRetriever;

public class SetLastResultUseCaseControllerTest {

	@Mock
	CurrentExerciseHolder currentExerciseHolderMock;
	@Mock
	Exercise exerciseMock;
	@Mock
	SetRetriever setRetrieverMock;
	@Mock
	Set setMock;
	@Mock
	StartValueSetter startValueSetterMock;
	
	SetLastResultUseCaseController sut;
	SystemEventListener sutAsSystemEventListener;
		
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		SetLastResultUseCaseControllerImpl sutImpl = new SetLastResultUseCaseControllerImpl(currentExerciseHolderMock,setRetrieverMock, startValueSetterMock);
		sut = sutImpl;
		sutAsSystemEventListener = sutImpl;
	}
	
	@Test
	public void gets_and_sets_last_set_when_gui_has_been_created(){
		when(currentExerciseHolderMock.getCurrentExercise()).thenReturn(exerciseMock);
		when(setRetrieverMock.getLastSetForExercise(exerciseMock)).thenReturn(setMock);
		
		sutAsSystemEventListener.onUICreated();
		
		verify(startValueSetterMock).setStartValues(setMock);
	}
	
	@Test
	public void doesnt_set_if_last_set_is_null(){
		when(currentExerciseHolderMock.getCurrentExercise()).thenReturn(exerciseMock);
		when(setRetrieverMock.getLastSetForExercise(exerciseMock)).thenReturn(null);
		
		sutAsSystemEventListener.onUICreated();
		
		verifyZeroInteractions(startValueSetterMock);
	}
	
}
