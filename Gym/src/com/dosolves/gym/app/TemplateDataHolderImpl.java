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
		ret.add(createChestCategory());
		ret.add(createStomachCategory());
		ret.add(createLegsCategory());
		ret.add(createBicepsCategory());
		ret.add(createTricepsCategory());
		ret.add(createShouldersCategory());
		
		return ret;
	}

	private CategoryTemplate createShouldersCategory() {
		CategoryTemplate shouldersCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.shoulders));
		
		ExerciseTemplate shoulderPress = new ExerciseTemplate(ResourcesUtils.getString(R.string.shoulder_press));
		ExerciseTemplate lateralRaise = new ExerciseTemplate(ResourcesUtils.getString(R.string.lateral_raise));
		
		shouldersCategory.addExercise(shoulderPress);
		shouldersCategory.addExercise(lateralRaise);
		
		return shouldersCategory;
	}

	private CategoryTemplate createTricepsCategory() {
		CategoryTemplate tricepsCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.triceps));
		
		ExerciseTemplate pushdown = new ExerciseTemplate(ResourcesUtils.getString(R.string.pushdown));
		ExerciseTemplate extension = new ExerciseTemplate(ResourcesUtils.getString(R.string.extension));
		
		tricepsCategory.addExercise(pushdown);
		tricepsCategory.addExercise(extension);
		
		return tricepsCategory;
	}

	private CategoryTemplate createBicepsCategory() {
		CategoryTemplate bicepsCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.biceps));
		
		ExerciseTemplate barbellCurl = new ExerciseTemplate(ResourcesUtils.getString(R.string.barbell_curl));
		ExerciseTemplate curl = new ExerciseTemplate(ResourcesUtils.getString(R.string.curl));
		ExerciseTemplate hammerCurl = new ExerciseTemplate(ResourcesUtils.getString(R.string.hammer_curl));
		
		bicepsCategory.addExercise(barbellCurl);
		bicepsCategory.addExercise(curl);
		bicepsCategory.addExercise(hammerCurl);
		
		return bicepsCategory;
	}

	private CategoryTemplate createStomachCategory() {
		CategoryTemplate stomachCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.abs));
		
		ExerciseTemplate crunch = new ExerciseTemplate(ResourcesUtils.getString(R.string.crunch));
		ExerciseTemplate legRaise = new ExerciseTemplate(ResourcesUtils.getString(R.string.leg_raise));
		
		stomachCategory.addExercise(crunch);
		stomachCategory.addExercise(legRaise);
		
		return stomachCategory;
	}

	private CategoryTemplate createLegsCategory() {
		CategoryTemplate legsCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.legs));
		
		ExerciseTemplate squat = new ExerciseTemplate(ResourcesUtils.getString(R.string.squat));
		ExerciseTemplate calfRaise = new ExerciseTemplate(ResourcesUtils.getString(R.string.calf_raise));
		
		legsCategory.addExercise(squat);
		legsCategory.addExercise(calfRaise);
		
		return legsCategory;
	}

	private CategoryTemplate createChestCategory() {
		CategoryTemplate chestCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.chest));
		
		ExerciseTemplate benchPress = new ExerciseTemplate(ResourcesUtils.getString(R.string.bench_press));
		ExerciseTemplate pushUp = new ExerciseTemplate(ResourcesUtils.getString(R.string.push_up));
		
		chestCategory.addExercise(benchPress);
		chestCategory.addExercise(pushUp);
		
		return chestCategory;
	}

	private CategoryTemplate createBackCategory() {
		CategoryTemplate backCategory = new CategoryTemplate(ResourcesUtils.getString(R.string.back));
		
		ExerciseTemplate deadLiftExercise = new ExerciseTemplate(ResourcesUtils.getString(R.string.deadlift));
		ExerciseTemplate pullUpExercise = new ExerciseTemplate(ResourcesUtils.getString(R.string.pull_up));
		
		backCategory.addExercise(deadLiftExercise);
		backCategory.addExercise(pullUpExercise);
		
		return backCategory;
	}

}

