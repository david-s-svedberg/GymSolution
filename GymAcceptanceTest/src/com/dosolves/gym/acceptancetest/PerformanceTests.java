package com.dosolves.gym.acceptancetest;

import android.content.Intent;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.app.category.CategoryModelFactoryImpl;
import com.dosolves.gym.app.exercise.ExerciseModelFactoryImpl;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;
import com.robotium.solo.Solo;

public class PerformanceTests extends CleanDbTestCase<PerformanceActivity>{

	private static final String EXERCISE_NAME = "exerciseName";
	private static final String CATEGORY_NAME = "categoryName";
	
	private Solo solo;
	private static Exercise exercise;
	
	public PerformanceTests(){
		super(PerformanceActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
	    super.setUp();
        Intent intent = new Intent();
		intent.putExtra(PerformanceActivity.EXERCISE_BUNDLE_KEY, exercise);
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());
    }
	
	@Override
	protected void setupDbHook() {
		createSingleExercise();		
	}
	
	private void createSingleExercise() {
		Category category = createSingleCategory();
		ExerciseModelFactoryImpl factory = new ExerciseModelFactoryImpl();
		int categoryId = category.getId();
		(factory.createUpdater()).create(EXERCISE_NAME, categoryId);
		exercise = (factory.createRetriever()).getExercisesInCategory(categoryId).get(0);
	}
	
	private Category createSingleCategory() {
		CategoryModelFactoryImpl factory = new CategoryModelFactoryImpl();
		(factory.createUpdater()).create(CATEGORY_NAME);
		return (factory.createRetriever()).getCategories().get(0);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();		
	}
		
	@LargeTest
	public void test_inputting_new_set_should_update_list_with_result(){
		solo.enterText((EditText)solo.getView(R.id.weightInput), "50");
		solo.enterText((EditText)solo.getView(R.id.repsInput), "12");
		solo.clickOnView(solo.getView(R.id.enterSetButton));
		ListView list = (ListView)solo.getView(R.id.previousWorkoutsListView);
		assertEquals(1, list.getCount());
	}
	
	@Override
	protected int numberOfTestCases() {
		return 1;
	}
	
}
