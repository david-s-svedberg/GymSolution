package com.dosolves.gym.domain;

import java.util.List;

import com.dosolves.gym.domain.category.CategoryTemplate;
import com.dosolves.gym.domain.category.data.CategoryCreator;
import com.dosolves.gym.domain.exercise.ExerciseTemplate;
import com.dosolves.gym.domain.exercise.data.ExerciseCreator;

public class AddDefaultExercisesUseCaseImpl implements AddDefaultExercisesUseCase {

	private UserAsker userAsker;
	private TemplateDataHolder templateData;
	private CategoryCreator categoryCreator;
	private ExerciseCreator exerciseCreator;

	public AddDefaultExercisesUseCaseImpl(UserAsker userAsker, 
										  TemplateDataHolder templateData, 
										  CategoryCreator categoryCreator, 
										  ExerciseCreator exerciseCreator) {
		this.userAsker = userAsker;
		this.templateData = templateData;
		this.categoryCreator = categoryCreator;
		this.exerciseCreator = exerciseCreator;
	}

	@Override
	public void runUseCase() {
		userAsker.askUser(new AbstractUserResponseListener() {

			@Override
			public void yes() {
				createDefaultExercises();
			}
			
		});		
	}
	
	private void createDefaultExercises() {
		List<CategoryTemplate> data = templateData.getTemplateData();
		for(CategoryTemplate currentCategory : data){
			int createdCategoryId = categoryCreator.create(currentCategory.getName());
			for(ExerciseTemplate currentExercise : currentCategory.getExercises()){
				exerciseCreator.create(currentExercise.getName(), createdCategoryId);
			}
		}
	}

}
