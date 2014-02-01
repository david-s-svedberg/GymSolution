package com.dosolves.gym.domain.performance;

import java.util.List;

import com.dosolves.gym.domain.exercise.Exercise;

public interface SetRetriever {

	List<Set> getSetsInExercise(Exercise exercise);

}
