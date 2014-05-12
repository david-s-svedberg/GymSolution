package com.dosolves.gym.app;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.TemplateDataHolder;
import com.dosolves.gym.domain.category.CategoryTemplate;
import com.dosolves.gym.domain.exercise.ExerciseTemplate;
import com.dosolves.gym.utils.ResourcesUtils;

public class TemplateDataHolderImpl implements TemplateDataHolder {

	@Override
	public List<CategoryTemplate> getTemplateData() {
		List<CategoryTemplate> ret = new ArrayList<CategoryTemplate>();
		
		ret.add(createBackCategory());
		
		return null;
	}

	private CategoryTemplate createBackCategory() {
		CategoryTemplate backCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.back));
		ExerciseTemplate deadLiftExercise = new ExerciseTemplate(ResourcesUtils.getString(R.string.deadlift));
		backCategory.addExercise(deadLiftExercise);
		return backCategory;
	}

}
