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
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.PerformanceController;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetMenuDialogShower;
import com.dosolves.gym.domain.performance.data.PerformanceBuilder;
import com.dosolves.gym.domain.performance.data.SetRetriever;
import com.dosolves.gym.domain.performance.data.SetUpdater;

@RunWith(RobolectricTestRunner.class)
public class PerformanceControllerTest {

	private static final double NEW_WEIGHT = 50.55;
	private static final int NEW_REPS = 12;
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
	@Mock
	EditSetDialogShower editSetDialogShowerMock;
	@Mock
	SetMenuDialogShower setMenuDialogShowerMock;
	
	
	Exercise exerciseMock;
	List<Performance> performancesMock;	
	List<Set> setsMock;
	Set setMock;
	
	PerformanceController sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceController(adapterMock, 
										exerciseHolderMock, 
										retrieverMock, 
										performanceBuilderMock,
										updaterMock,
										editSetDialogShowerMock,
										setMenuDialogShowerMock);
		exerciseMock = new Exercise(EXERCISE_ID, 34, "exerciseName");
		setsMock = createSets();
		performancesMock = createPerformances();
	}
	
	@Test
    public void onReadyToGetData_updates_performances(){           
		stubPerformanceUpdating();
        
        sut.onReadyToGetData();
        
        verifyPerformancesHaveBeenUpdated();
    }
	
	@Test
    public void calls_updater_when_new_set_should_be_registered(){           
		when(exerciseHolderMock.getCurrentExercise()).thenReturn(exerciseMock);
		
		sut.onNewSetShouldBeCreated(REPS,WEIGHT);
        
        verify(updaterMock).create(EXERCISE_ID, REPS, WEIGHT);
    }
	
	@Test
    public void updates_performances_after_new_set_has_been_created(){
		stubPerformanceUpdating();
		
		sut.onNewSetShouldBeCreated(REPS,WEIGHT);
        
        verifyPerformancesHaveBeenUpdated();
    }
	
	@Test
    public void shows_set_options_menu_when_requested(){
		sut.onSetMenuRequested(setMock);
        
        verify(setMenuDialogShowerMock).show(setMock, sut, sut);
    }
	
	@Test
    public void shows_edit_set_dialog_when_requested(){
		sut.onEditSetDialogRequested(setMock);
        
        verify(editSetDialogShowerMock).show(setMock, sut);
    }
	
	@Test
    public void calls_updater_when_set_should_be_updated(){
		sut.onSetShouldBeUpdated(setMock, NEW_REPS, NEW_WEIGHT);
        
        verify(updaterMock).update(setMock, NEW_REPS,NEW_WEIGHT);
    }
	
	@Test
    public void updates_sets_after_update(){
		stubPerformanceUpdating();
		
		sut.onSetShouldBeUpdated(setMock, NEW_REPS, NEW_WEIGHT);
        
        verifyPerformancesHaveBeenUpdated();
    }
	
	@Test
    public void calls_updater_when_set_should_be_deleted(){
		sut.onSetShouldBeDeleted(setMock);
        
        verify(updaterMock).delete(setMock);
    }
	
	@Test
    public void updates_sets_after_delete(){
		stubPerformanceUpdating();
		
		sut.onSetShouldBeDeleted(setMock);
        
        verifyPerformancesHaveBeenUpdated();
    }

	private void stubPerformanceUpdating() {
		when(exerciseHolderMock.getCurrentExercise()).thenReturn(exerciseMock);
		when(retrieverMock.getSetsInExercise(exerciseMock)).thenReturn(setsMock);
        when(performanceBuilderMock.build(setsMock)).thenReturn(performancesMock);
	}
	
	private ArrayList<Performance> createPerformances() {
		ArrayList<Performance> performances = new ArrayList<Performance>();		
		performances.add(new Performance(setsMock));
		return performances;
	}

	private List<Set> createSets() {
		List<Set> sets = new ArrayList<Set>();
		setMock = new Set(12,312,12,55.5,new Date());
		sets.add(setMock);
		return sets;
	}

	private void verifyPerformancesHaveBeenUpdated() {
		verify(adapterMock).clear();
		verify(adapterMock).setPerformances(performancesMock);
		verify(adapterMock).notifyDataSetChanged();
	}
	
}
