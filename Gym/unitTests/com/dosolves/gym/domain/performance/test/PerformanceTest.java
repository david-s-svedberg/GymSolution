package com.dosolves.gym.domain.performance.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.Set;

public class PerformanceTest {
	
	private static final double WEIGHT = 50.5;
	private static final int REPS = 12;
	private static final int EXERCISE_ID = 534;
	private static final int SET_ID = 123;

	
	List<Set> sets;
	
	Performance sut;
	private Date earlierDate;
	private Date laterDate;
	
	@Before
	public void setUp(){
		sets = new ArrayList<Set>();
		earlierDate = createDateWithDayHourAndMinute(1,23, 59);
		laterDate = createDateWithDayHourAndMinute(2,1, 0);
		sets.add(createSetWithDate(earlierDate));
		sets.add(createSetWithDate(laterDate));
		sut = new Performance(sets);
	}
	
	@SuppressWarnings("deprecation")
	private Date createDateWithDayHourAndMinute(int day, int hour, int minute) {
		return new Date(100,1,day,hour,minute,0);
	}

	@Test
	public void getDateReturnsEarliestDateFromSets(){
		assertEquals(earlierDate, sut.getDate());
	}
	
	private Set createSetWithDate(Date date) {
		return new Set(SET_ID, EXERCISE_ID, REPS,WEIGHT,date);
	}
	
}
