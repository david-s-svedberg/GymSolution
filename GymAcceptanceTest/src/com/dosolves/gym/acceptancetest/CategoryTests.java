package com.dosolves.gym.acceptancetest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.dosolves.gym.R;
import com.dosolves.gym.app.database.SQLiteOpenHelperSingeltonHolder;
import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.robotium.solo.Solo;

public class CategoryTests extends ActivityInstrumentationTestCase2<CategoriesActivity>{

	private static final String TEST_ADD_CATEGORY_TEXT = "ADD_CATEGORY_TEXT";
	private static final int TIME_TO_WAIT_FOR_DIALOG = 5000;
	private static final String TEST_DELETE_CATEGORY_TEXT = "DELETE_CATEGORY_TEXT";
	
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
		createCategory(TEST_ADD_CATEGORY_TEXT);
		
		assertTrue("Test category not found in list", solo.searchText(TEST_ADD_CATEGORY_TEXT));
		
		deleteCreatedCategory(TEST_ADD_CATEGORY_TEXT);
	}
	
	@LargeTest
	public void test_delete_category_should_remove_category_from_list(){
		createCategory(TEST_DELETE_CATEGORY_TEXT);
		deleteCreatedCategory(TEST_DELETE_CATEGORY_TEXT);
		assertFalse("Category was not deleted", solo.searchText(TEST_DELETE_CATEGORY_TEXT));
	}
	
	@LargeTest
	public void test_category_with_same_name_as_another_cant_be_added(){
		createCategory(TEST_ADD_CATEGORY_TEXT);
		createCategory(TEST_ADD_CATEGORY_TEXT);
		deleteCreatedCategory(TEST_ADD_CATEGORY_TEXT);
		
		
		assertFalse("Possible to add more then one category with the same name", solo.searchText(TEST_ADD_CATEGORY_TEXT));
		
	}
	
	@LargeTest
	public void test_clicking_category_opens_exercises_activity(){
		try{
			createCategory(TEST_ADD_CATEGORY_TEXT);
			
			solo.clickOnText(TEST_ADD_CATEGORY_TEXT);
			assertTrue("Exercise activity was not shown",solo.waitForActivity(ExercisesActivity.class,TIME_TO_WAIT_FOR_DIALOG));
			
			solo.goBack();
		}
		finally{
			deleteCreatedCategory(TEST_ADD_CATEGORY_TEXT);
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
		solo.clickOnText("Delete");
	}
	
}
