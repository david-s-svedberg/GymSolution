package com.dosolves.gym.acceptancetest;

import junit.framework.AssertionFailedError;
import android.test.suitebuilder.annotation.LargeTest;

import com.dosolves.gym.R;
import com.dosolves.gym.app.CommonModelFactory;
import com.dosolves.gym.app.CommonModelFactoryImpl;
import com.dosolves.gym.app.category.gui.CategoriesActivity;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.exercise.gui.ExercisesActivity;
import com.dosolves.gym.domain.category.Category;
import com.robotium.solo.Solo;

public class CategoryTests extends CleanDbTestCase<CategoriesActivity>{

	private static final String CATEGORY_NAME = "categoryName";
	private static final String EXERCISE_NAME = "exerciseName";
	
	private static final int TIME_TO_WAIT_FOR_DIALOG = 5000;
	
	private Solo solo;
	
	public CategoryTests(){
		super(CategoriesActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        SQLiteOpenHelperSingeltonHolder.useTestDb();
        solo = new Solo(getInstrumentation(), getActivity());
    }
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
	
	@LargeTest
	public void test_Adding_new_category_should_add_category_to_list(){
		createCategory(CATEGORY_NAME);
		
		assertTrue("Test category not found in list", solo.searchText(CATEGORY_NAME));
		
		deleteCreatedCategory(CATEGORY_NAME);
	}
	
	@LargeTest
	public void test_delete_category_should_remove_category_from_list(){
		createCategory(CATEGORY_NAME);
		deleteCreatedCategory(CATEGORY_NAME);
		assertFalse("Category was not deleted", solo.searchText(CATEGORY_NAME));
	}
	
	@LargeTest
	public void test_category_with_same_name_as_another_cant_be_added(){
		createCategory(CATEGORY_NAME);
		createCategory(CATEGORY_NAME);
		deleteCreatedCategory(CATEGORY_NAME);
		
		assertFalse("Possible to add more then one category with the same name", solo.searchText(CATEGORY_NAME));
		
	}
	
	@LargeTest
	public void test_clicking_category_opens_exercises_activity(){
		try{
			createCategory(CATEGORY_NAME);
			
			solo.clickOnText(CATEGORY_NAME);
			assertTrue("Exercise activity was not shown",solo.waitForActivity(ExercisesActivity.class,TIME_TO_WAIT_FOR_DIALOG));
			
			solo.goBack();
		}
		finally{
			cleanUpCreatedCategory(CATEGORY_NAME);
		}
	}
	
	@LargeTest
	public void test_delete_category_that_has_exercises_displays_dialog_deletes_on_yes(){
		try{
			createCategory(CATEGORY_NAME);
			programaticallyCreateSingleExerciseOnOnlyCategory();
			
			deleteCreatedCategory(CATEGORY_NAME);
			assertTrue("warning dialog didn't open", solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
			assertTrue("Dialog text not found", solo.searchText(getActivity().getString(R.string.category_has_exercises)));
			assertTrue("Dialog text not found", solo.searchText(getActivity().getString(R.string.delete_anyway)));
			solo.clickOnButton(getActivity().getString(R.string.yes));
			assertFalse("Category was not deleted", solo.searchText(CATEGORY_NAME));
		}
		finally{
			cleanUpCreatedCategory(CATEGORY_NAME);
		}
	}
	
	@LargeTest
	public void test_delete_category_that_has_exercises_displays_dialog_but_does_not_delete_on_no(){
		try{
			createCategory(CATEGORY_NAME);
			programaticallyCreateSingleExerciseOnOnlyCategory();
			
			deleteCreatedCategory(CATEGORY_NAME);
			assertTrue("warning dialog didn't open", solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
			assertTrue("Dialog text not found", solo.searchText(getActivity().getString(R.string.category_has_exercises)));
			assertTrue("Dialog text not found", solo.searchText(getActivity().getString(R.string.delete_anyway)));
			solo.clickOnButton(getActivity().getString(R.string.no));
			assertTrue("Category was deleted even though it shouldn't have been", solo.searchText(CATEGORY_NAME));
		}
		finally{
			cleanUpCreatedCategory(CATEGORY_NAME);
		}
	}
	
	public void test_delete_category_that_has_no_exercises_does_not_displays_dialog(){
		try{
			createCategory(CATEGORY_NAME);
			
			deleteCreatedCategory(CATEGORY_NAME);
			assertFalse("warning dialog opened even though it should not", solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
			
			assertFalse("Category was not deleted", solo.searchText(CATEGORY_NAME));
		}
		finally{
			cleanUpCreatedCategory(CATEGORY_NAME);
		}
	}

	private void createCategory(String categoryName) {
		solo.clickOnActionBarItem(R.id.add_item);
		assertTrue("Create category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, categoryName);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
	}
	
	private void deleteCreatedCategory(String categoryName) {
		solo.clickLongOnText(categoryName);
		solo.waitForView(R.id.delete_menu_item);
		solo.clickOnView(getActivity().findViewById(R.id.delete_menu_item));
	}
	
	private void cleanUpCreatedCategory(String categoryName) {
		try{
			if(solo.searchText(categoryName)){
				solo.clickLongOnText(categoryName);
				solo.waitForView(R.id.delete_menu_item);
				solo.clickOnView(getActivity().findViewById(R.id.delete_menu_item));
				solo.clickOnButton(getActivity().getString(R.string.yes));
			}
		}
		catch(AssertionFailedError ex){
			//Ignore just for cleanUp
		}
	}
	
	private void programaticallyCreateSingleExerciseOnOnlyCategory() {
		CommonModelFactory factory = new CommonModelFactoryImpl();
		
		Category onlyCategory = (factory.getCategoryRetriever()).getCategories().get(0);
		
		(factory.getExerciseUpdater()).create(EXERCISE_NAME, onlyCategory.getId());
	}
	
	@Override
	protected int numberOfTestCases() {
		return 7;
	}
	
}
