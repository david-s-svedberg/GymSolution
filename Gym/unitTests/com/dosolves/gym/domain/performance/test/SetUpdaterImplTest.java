package com.dosolves.gym.domain.performance.test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.CurrentDateGiver;
import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.data.SetStructureGiver;
import com.dosolves.gym.domain.performance.data.SetUpdater;
import com.dosolves.gym.domain.performance.data.SetUpdaterImpl;

@RunWith(RobolectricTestRunner.class)
public class SetUpdaterImplTest {
	
	private static final int SET_ID = 123;
	private static final long DATE_MILLISECONDS = 3456L;
	private static final double WEIGHT = 55.5;
	private static final int EXERCISE_ID = 123;
	private static final int REPS = 12;

	@Mock
	DataAccess dataAccessMock;
	@Mock
	CurrentDateGiver currentDateGiverMock;
	
	SetUpdater sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);	
		sut = new SetUpdaterImpl(dataAccessMock, currentDateGiverMock);
	}
	
	@Test
	public void create_calls_dataAccess_with_correct_map(){
		when(currentDateGiverMock.getCurrentDate()).thenReturn(DATE_MILLISECONDS);
		
		sut.create(EXERCISE_ID, REPS, WEIGHT);
		
		verify(dataAccessMock).create(eq(SetStructureGiver.SET_TYPE_NAME_PLURAL), argThat(new ArgumentMatcher<Map<String,Object>>(){
			
			@Override
			public boolean matches(Object arg0) {
				if(!(arg0 instanceof Map<?,?>) || arg0 == null)
					return false;
				@SuppressWarnings("unchecked")
				Map<String, Object> values = (Map<String, Object>) arg0;
				
				return mapContainsExerciseId(values, EXERCISE_ID) && 
					   mapContainsReps(values, REPS) && 
					   mapContainsWeight(values, WEIGHT) &&
					   mapContainsDate(values, DATE_MILLISECONDS);
			}

			private boolean mapContainsDate(Map<String, Object> values, long currentDateMilliseconds) {
				return values.get(SetStructureGiver.DATE_PROPERTY_NAME).equals(DATE_MILLISECONDS);
			}

			private boolean mapContainsWeight(Map<String, Object> values, double weight) {
				return values.get(SetStructureGiver.WEIGHT_PROPERTY_NAME).equals(WEIGHT);
			}

			private boolean mapContainsReps(Map<String, Object> values, int reps) {
				return values.get(SetStructureGiver.REPS_PROPERTY_NAME).equals(REPS);
			}

			private boolean mapContainsExerciseId(Map<String, Object> values, int exerciseId) {
				return values.get(SetStructureGiver.EXERCISE_ID_PROPERTY_NAME).equals(EXERCISE_ID);
			}
		}));
	}
	
	@Test
	public void delete_calls_dataAccess_with_correct_idcolumnName(){
		Set set = new Set(SET_ID,EXERCISE_ID,REPS,WEIGHT, new Date(DATE_MILLISECONDS));
		
		sut.delete(set);
		
		verify(dataAccessMock).delete(SetStructureGiver.SET_TYPE_NAME_PLURAL,SetStructureGiver.ID_PROPERTY_NAME, SET_ID);
	}
}
