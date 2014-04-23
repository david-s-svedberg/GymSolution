package com.dosolves.gym.domain.exercise.test;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.data.DataAccess;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.data.ExerciseStructureGiver;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdaterImpl;

@RunWith(RobolectricTestRunner.class)
public class ExerciseUpdaterImplTest {

	private static final int CATEGORY_ID = 235;

	private static final int EXERCISE_ID = 123;

	private static final String NEW_EXERCISE_NAME = "newExerciseName";

	private static final String EXERCISE_NAME = "EXERCISE_NAME";

	@Mock
	DataAccess dataAccessMock;
	
	ExerciseUpdater sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);	
		sut = new ExerciseUpdaterImpl(dataAccessMock);
	}
	
	@Test
	public void create_calls_dataAccess_with_correct_map(){		
		sut.create(NEW_EXERCISE_NAME, CATEGORY_ID);
		
		verify(dataAccessMock).create(eq(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL), argThat(new ArgumentMatcher<Map<String,Object>>(){
			
			@Override
			public boolean matches(Object arg0) {
				if(!(arg0 instanceof Map<?,?>) || arg0 == null)
					return false;
				@SuppressWarnings("unchecked")
				Map<String, Object> values = (Map<String, Object>) arg0;
				if (mapContainsCorrectExerciseName(values) && mapContainsCorrectCategoryId(values))
					return true;				
				else
					return false;
			}

			private boolean mapContainsCorrectCategoryId(Map<String, Object> values) {
				return (int)values.get(ExerciseStructureGiver.CATEGORY_ID_PROPERTY_NAME) == CATEGORY_ID;
			}

			private boolean mapContainsCorrectExerciseName(Map<String, Object> values) {
				return values.get(ExerciseStructureGiver.NAME_PROPERTY_NAME).equals(NEW_EXERCISE_NAME);
			}			
		}));
	}
	
	@Test
	public void delete_calls_dataAccess_with_correct_idcolumnName(){
		sut.delete(EXERCISE_ID);
		
		verify(dataAccessMock).delete(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL,ExerciseStructureGiver.ID_PROPERTY_NAME, EXERCISE_ID);
	}
	
	@Test
	public void rename_calls_dataAccess_with_correct_parameters(){
		Exercise exercise = new Exercise(EXERCISE_ID, CATEGORY_ID, EXERCISE_NAME);
		
		sut.rename(exercise, NEW_EXERCISE_NAME);
		
		verify(dataAccessMock).update(eq(ExerciseStructureGiver.EXERCISE_TYPE_NAME_PLURAL),eq(ExerciseStructureGiver.ID_PROPERTY_NAME), eq(EXERCISE_ID), argThat(new ArgumentMatcher<Map<String,Object>>(){
			
			@Override
			public boolean matches(Object arg0) {
				if(!(arg0 instanceof Map<?,?>) || arg0 == null)
					return false;
				@SuppressWarnings("unchecked")
				Map<String, Object> values = (Map<String, Object>) arg0;
				if (mapContainsCorrectExerciseName(values))
					return true;				
				else
					return false;
			}

			private boolean mapContainsCorrectExerciseName(Map<String, Object> values) {
				return values.get(ExerciseStructureGiver.NAME_PROPERTY_NAME).equals(NEW_EXERCISE_NAME);
			}			
		}));
	}
}
