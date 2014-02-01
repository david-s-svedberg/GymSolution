package com.dosolves.gym.domain.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExercisePerformanceBuilder {

	private static final int TWO_HOURS_IN_MILLISECONDS = 1000*60*60*2;

	public List<ExercisePerformance> build(List<Set> allSets) {
		List<ExercisePerformance> performances = new ArrayList<ExercisePerformance>();
		ArrayList<Set> currentExercisePerformanceSets = new ArrayList<Set>();
		Set previousSet = null;
		
		for(Set currentSet:allSets){
			if(previousSet != null && moreThenTwoHoursBetween(previousSet, currentSet)){
				performances.add(new ExercisePerformance(currentExercisePerformanceSets));
				currentExercisePerformanceSets = new ArrayList<Set>();					
			}
			currentExercisePerformanceSets.add(currentSet);	
			
			previousSet = currentSet;
		}
		
		if(!currentExercisePerformanceSets.isEmpty()){
			performances.add(new ExercisePerformance(currentExercisePerformanceSets));
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
