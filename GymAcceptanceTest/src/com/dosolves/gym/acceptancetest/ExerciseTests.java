package com.dosolves.gym.acceptancetest;

import android.content.Intent;
import android.test.suitebuilder.annotation.LargeTest;

import com.dosolves.gym.R;
import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.CommonModelFactoryImpl;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.category.Category;
import com.robotium.solo.Solo;

public class ExerciseTests extends CleanDbTestCase<ExercisesActivity>{

	private static final String CATEGORY_NAME = "categoryName";
	private static final String EXERCISE_NAME = "exerciseName";
	
	private static final int TIME_TO_WAIT_FOR_DIALOG = 5000;
	
	private Solo solo;
	
	private static Category category;
	
	public ExerciseTests(){
		super(ExercisesActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
	    super.setUp();
        Intent intent = new Intent();
		intent.putExtra(ExercisesActivity.CATEGORY_BUNDLE_KEY, category);
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());
    }
	
	@Override
	protected void setupDbHook() {
		createSingleCategory();		
	}

	private void createSingleCategory() {
		CommonModelFactory factory = new CommonModelFactoryImpl();
		(factory.getCategoryUpdater()).create(CATEGORY_NAME);
		category = (factory.getCategoryRetriever()).getCategories().get(0);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();		
	}
		
	@LargeTest
	public void test_Adding_new_exercise_should_add_exercise_to_list(){
		createExercise(EXERCISE_NAME);
		
		assertTrue("couldn't find new exercise in list", solo.searchText(EXERCISE_NAME));
		
		deleteCreatedExercise(EXERCISE_NAME);		
	}
	
	@LargeTest
	public void test_delete_exercise_should_remove_exercise_from_list(){
		createExercise(EXERCISE_NAME);
		deleteCreatedExercise(EXERCISE_NAME);
		
		assertFalse("Exercise was not deleted", solo.searchText(EXERCISE_NAME));
	}
	
	@LargeTest
	public void test_exercise_with_same_name_as_another_cant_be_added(){
		createExercise(EXERCISE_NAME);
		createExercise(EXERCISE_NAME);
		deleteCreatedExercise(EXERCISE_NAME);
		
		assertFalse("Possible to add more then one exercise with the same name", solo.searchText(EXERCISE_NAME));
		
	}
	
	@LargeTest
	public void test_clicking_exercise_opens_exerciseInput_activity(){
		try{
			createExercise(EXERCISE_NAME);
			
			solo.clickOnText(EXERCISE_NAME);
			
			assertTrue("Exercise activity was not shown",solo.waitForActivity(PerformanceActivity.class,TIME_TO_WAIT_FOR_DIALOG));
			
			solo.goBack();			
		}
		finally{
			deleteCreatedExercise(EXERCISE_NAME);
		}
	}
	
	@Override
	protected int numberOfTestCases() {
		return 4;
	}
	

	private void createExercise(String exerciseName) {
		solo.clickOnActionBarItem(R.id.add_item);
		assertTrue("Create exercise dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, exerciseName);
		solo.clickOnButton("Ok");
		assertTrue("Create exercise dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
	}

	private void deleteCreatedExercise(String exerciseName) {
		solo.clickLongOnText(exerciseName);
		solo.clickOnView(getActivity().findViewById(R.id.delete_menu_item));
	}
	
}
