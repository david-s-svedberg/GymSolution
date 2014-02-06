package com.dosolves.gym.domain.performance.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;

@RunWith(RobolectricTestRunner.class)
public class PerformanceControllerTest {

	private static final double WEIGHT = 50.5;
	private static final int REPS = 12;
	private static final int EXERCISE_ID = 12;
	@Mock
	SetRetriever retrieverMock;
	@Mock
	PerformanceBuilder performanceBuilderMock;
	@Mock
	PerformanceAdapter adapterMock;
	@Mock
	CurrentExerciseHolder exerciseHolderMock;
	@Mock
	SetUpdater updaterMock;
	
	Exercise exercise;
	List<Performance> performances;	
	List<Set> sets;
	
	PerformanceController sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceController(adapterMock, 
										exerciseHolderMock, 
										retrieverMock, 
										performanceBuilderMock,
										updaterMock);
		exercise = new Exercise(EXERCISE_ID, 34, "exerciseName");
		sets = createSets();
		performances = createPerformances();
	}
	
	@Test
    public void onReadyToGetData_updates_performances(){           
		stubPerformanceUpdating();
        
        sut.onReadyToGetData();
        
        verifyPerformancesHaveBeenUpdated();
    }
	
	@Test
    public void calls_updater_when_new_set_should_be_registered(){           
		when(exerciseHolderMock.getCurrentExercise()).thenReturn(exercise);
		
		sut.onNewSetShouldBeCreated(REPS,WEIGHT);
        
        verify(updaterMock).create(EXERCISE_ID, REPS, WEIGHT);
    }
	
	@Test
    public void updates_performances_after_new_set_has_been_created(){
		stubPerformanceUpdating();
		
		sut.onNewSetShouldBeCreated(REPS,WEIGHT);
        
        verifyPerformancesHaveBeenUpdated();
    }

	private void stubPerformanceUpdating() {
		when(exerciseHolderMock.getCurrentExercise()).thenReturn(exercise);
		when(retrieverMock.getSetsInExercise(exercise)).thenReturn(sets);
        when(performanceBuilderMock.build(sets)).thenReturn(performances);
	}
	
	private ArrayList<Performance> createPerformances() {
		ArrayList<Performance> performances = new ArrayList<Performance>();		
		performances.add(new Performance(sets));
		return performances;
	}

	private List<Set> createSets() {
		List<Set> sets = new ArrayList<Set>();
		Set set = new Set(12,312,12,55.5,new Date());
		sets.add(set);
		return sets;
	}

	private void verifyPerformancesHaveBeenUpdated() {
		verify(adapterMock).clear();
		verify(adapterMock).setPerformances(performances);
		verify(adapterMock).notifyDataSetChanged();
	}
	
}
