package com.dosolves.gym.app.gui.exercise.test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.app.gui.exercise.ContextExerciseOpener;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseOpener;

@RunWith(RobolectricTestRunner.class)
public class ContextExerciseOpenerTest {

	private static final int CATEGORY_ID = 3546;
	private static final String EXERCISE_NAME = "EXERCISE_NAME";
	private static final int EXERCISE_ID = 234;
	
	@Mock
	Context contextMock;
	
	ExerciseOpener sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ContextExerciseOpener(contextMock);
	}
	
	
	@Ignore("Remember to decomment when input activity is to be implemented")
	@Test
	public void calls_startActivity_when_openExercise_is_called(){
		
		final Exercise exerciseMock = new Exercise(EXERCISE_ID, CATEGORY_ID, EXERCISE_NAME);
		sut.openExercise(exerciseMock);
		
		verify(contextMock).startActivity(argThat(new ArgumentMatcher<Intent>(){

			@Override
			public boolean matches(Object argument) {
				Intent intent = (Intent) argument;
				if (intentContainsExercise(exerciseMock, intent) && intentTargetsExerciseActivity(intent))
					return true;
				else
					return false;
			}

			private boolean intentTargetsExerciseActivity(Intent intent) {
				//assertTrue(intent.getComponent().getClassName().equals(ExerciseActivity.class.getName()));
				return true;
			}

			private boolean intentContainsExercise(final Exercise exercise, Intent intent) {
				Category category = (Category)intent.getExtras().get(ExercisesActivity.CATEGORY_BUNDLE_KEY);
				assertTrue(category.equals(exercise));
				return true;
			}
			
		}));
	}
	
}
