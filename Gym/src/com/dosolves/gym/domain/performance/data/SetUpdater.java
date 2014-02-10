package com.dosolves.gym.domain.performance.data;

import com.dosolves.gym.domain.performance.Set;

public interface SetUpdater {

	void create(int exerciseId, int reps, double weight);
	void delete(Set set);
	void update(Set set, int newReps, double newWeight);

}
