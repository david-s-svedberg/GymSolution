package com.dosolves.gym.domain.performance.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.performance.ExercisePerformance;
import com.dosolves.gym.domain.performance.ExercisePerformanceBuilder;
import com.dosolves.gym.domain.performance.Set;


@RunWith(RobolectricTestRunner.class)
public class ExercisePerformanceBuilderTest {

	private static final Date DATE = new Date();
	private static final double WEIGHT = 50.5;
	private static final int REPS = 12;
	private static final int EXERCISE_ID = 534;
	private static final int SET_ID = 123;
	ExercisePerformanceBuilder sut;
	private ArrayList<Set> sets;
	private Set set;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sets = new ArrayList<Set>();
		set = new Set(SET_ID, EXERCISE_ID,REPS,WEIGHT,DATE);
		
		sut = new ExercisePerformanceBuilder();
	}
	
	@Test
    public void returns_empty_exercisePerformance_list_for_empty_set_list(){
        assertTrue(sut.build(new ArrayList<Set>()).isEmpty());   
    }
	
	@Test
    public void returns_one_exercisePerformance_with_one_set_for_one_set_list(){
		sets.add(set);
		List<ExercisePerformance> exercisePerformances = sut.build(sets);
		assertEquals(1, exercisePerformances.size());
		assertEquals(1, exercisePerformances.get(0).getSets().size());
		assertEquals(set, exercisePerformances.get(0).getSets().get(0));
    }
	
	@Test
    public void returns_one_exercisePerformance_with_two_set_for_two_set_list_with_same_date(){
		sets.add(set);
		sets.add(set);
		List<ExercisePerformance> exercisePerformances = sut.build(sets);
		assertEquals(1, exercisePerformances.size());
		assertEquals(2, exercisePerformances.get(0).getSets().size());
		assertEquals(set, exercisePerformances.get(0).getSets().get(0));
		assertEquals(set, exercisePerformances.get(0).getSets().get(1));
    }
	
	@Test
    public void returns_two_exercisePerformance_with_one_set_each_for_two_set_list_with_dates_3_hours_apart(){
		sets.add(createSetWithHourAndMinute(1,0));
		sets.add(createSetWithHourAndMinute(4,0));
		List<ExercisePerformance> exercisePerformances = sut.build(sets);
		assertEquals(2, exercisePerformances.size());
		assertEquals(1, exercisePerformances.get(0).getSets().size());
		assertEquals(1, exercisePerformances.get(1).getSets().size());		
    }
	
	@Test
    public void returns_one_exercisePerformance_with_two_setsfor_two_set_list_with_dates_1_59_hours_apart(){
		sets.add(createSetWithHourAndMinute(1,0));
		sets.add(createSetWithHourAndMinute(2,59));
		List<ExercisePerformance> exercisePerformances = sut.build(sets);
		assertEquals(1, exercisePerformances.size());
		assertEquals(2, exercisePerformances.get(0).getSets().size());				
    }

	@SuppressWarnings("deprecation")
	private Set createSetWithHourAndMinute(int hour,int minute) {		
		return createSetWithDate(new Date(2000,1,1,hour,minute,0));
	}

	private Set createSetWithDate(Date date) {
		return new Set(SET_ID, EXERCISE_ID, REPS,WEIGHT,date);
	}
	
}
