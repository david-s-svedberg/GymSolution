package com.dosolves.gym.domain.set;

import java.util.List;

import com.dosolves.gym.domain.exercise.Exercise;

public interface SetRetriever {

	List<Set> getSetsInExercise(Exercise exercise);

}
