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

@RunWith(RobolectricTestRunner.class)
public class PerformanceControllerTest {

	@Mock
	SetRetriever retrieverMock;
	@Mock
	PerformanceBuilder performanceBuilderMock;
	@Mock
	PerformanceAdapter adapterMock;
	@Mock
	CurrentExerciseHolder exerciseHolderMock;
	
	Exercise exercise;
	List<Performance> performances;	
	List<Set> sets;
	
	PerformanceController sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceController(adapterMock, exerciseHolderMock, retrieverMock, performanceBuilderMock);
		exercise = new Exercise(12, 34, "exerciseName");
		sets = createSets();
		performances = createPerformances();
	}
	
	@Test
    public void onReadyToGetData_updates_performances(){           
		when(exerciseHolderMock.getCurrentExercise()).thenReturn(exercise);
		when(retrieverMock.getSetsInExercise(exercise)).thenReturn(sets);
        when(performanceBuilderMock.build(sets)).thenReturn(performances);
        
        sut.onReadyToGetData();
        
        verifyPerformancesHaveBeenUpdated();
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
