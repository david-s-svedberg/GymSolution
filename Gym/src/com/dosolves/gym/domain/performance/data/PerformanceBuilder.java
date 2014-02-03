package com.dosolves.gym.domain.performance.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.Set;


public class PerformanceBuilder {

	private static final int TWO_HOURS_IN_MILLISECONDS = 1000*60*60*2;

	public List<Performance> build(List<Set> allSets) {
		List<Performance> performances = new ArrayList<Performance>();
		ArrayList<Set> currentExercisePerformanceSets = new ArrayList<Set>();
		Set previousSet = null;
		
		for(Set currentSet:allSets){
			if(previousSet != null && moreThenTwoHoursBetween(previousSet, currentSet)){
				performances.add(new Performance(currentExercisePerformanceSets));
				currentExercisePerformanceSets = new ArrayList<Set>();					
			}
			currentExercisePerformanceSets.add(currentSet);	
			
			previousSet = currentSet;
		}
		
		if(!currentExercisePerformanceSets.isEmpty()){
			performances.add(new Performance(currentExercisePerformanceSets));
		}
		
		return performances;
	}

	private boolean moreThenTwoHoursBetween(Set previousSet, Set currentSet) {
		return millisecondsBetweenDates(previousSet.getDate(), currentSet.getDate()) > TWO_HOURS_IN_MILLISECONDS ;
	}

	private long millisecondsBetweenDates(Date first, Date second) {
		return Math.abs(first.getTime() - second.getTime());
	}

}
