package com.dosolves.gym.domain.performance;

import java.util.Date;
import java.util.List;


public class Performance {
	
	private List<Set> sets;
	
	public Performance(List<Set> sets){
		this.sets = sets;		
	}

	public List<Set> getSets() {
		return sets;
	}

	public Date getDate() {
		Date ret = new Date();
		for(Set current: sets)
			if(current.getDate().getTime()<ret.getTime())
				ret = current.getDate();		
		return ret;
	}

}
