package com.dosolves.gym.domain.performance.data;

import java.util.List;

import com.dosolves.gym.domain.performance.Set;

public class HighLevelSetIdRetriever implements SetIdRetriever {

	private SetRetriever setRetriever;

	public HighLevelSetIdRetriever(SetRetriever setRetriever) {
		this.setRetriever = setRetriever;
	}

	@Override
	public int[] getIds(int exerciseId) {
		List<Set> sets = setRetriever.getSetsInExercise(exerciseId);
		int[] ids = new int[sets.size()];
		
		for(int i = 0;i<sets.size();i++){
			ids[i] = sets.get(i).getId();
		}
		
		return ids;
	}

}
