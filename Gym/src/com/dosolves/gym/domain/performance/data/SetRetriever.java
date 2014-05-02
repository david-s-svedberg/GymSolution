package com.dosolves.gym.domain.performance.data;

import java.util.List;

import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.Set;

public interface SetRetriever {

	List<Set> getSetsInExercise(int exerciseId);
	Set getLastSetForExercise(Exercise exercise);
	Set getSet(int setId);

}
