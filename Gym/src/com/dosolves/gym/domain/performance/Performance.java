package com.dosolves.gym.domain.performance;

import java.util.List;


public class Performance {
	
	private List<Set> sets;
	
	public Performance(List<Set> sets){
		this.sets = sets;		
	}

	public List<Set> getSets() {
		return sets;
	}

}
