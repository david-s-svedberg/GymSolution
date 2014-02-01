package com.dosolves.gym.domain.performance;

import java.util.List;


public class ExercisePerformance {
	
	private List<Set> sets;
	
	public ExercisePerformance(List<Set> sets){
		this.sets = sets;		
	}

	public List<Set> getSets() {
		return sets;
	}

}
