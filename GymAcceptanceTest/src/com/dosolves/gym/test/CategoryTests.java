package com.dosolves.gym.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.category.CategoryActivity;
import com.robotium.solo.Solo;

public class CategoryTests extends ActivityInstrumentationTestCase2<CategoryActivity>{

	private static final String TEST_ADD_CATEGORY_TEXT = "ADD_CATEGORY_TEXT";
	private static final int TIME_TO_WAIT_FOR_DIALOG = 5000;
	private static final String TEST_DELETE_CATEGORY_TEXT = "DELETE_CATEGORY_TEXT";
	
	private Solo solo;
	
	public CategoryTests(){
		super(CategoryActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();        
        solo = new Solo(getInstrumentation(), getActivity());
    }
	
	@LargeTest
	public void test_Adding_new_category_should_add_category_to_list(){
		solo.clickOnActionBarItem(R.id.add_category);
		assertTrue("Create category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, TEST_ADD_CATEGORY_TEXT);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
		assertTrue("Test category not found in list", solo.searchText(TEST_ADD_CATEGORY_TEXT));
		
		removeCreatedCategory(TEST_ADD_CATEGORY_TEXT);
	}
	
	private void removeCreatedCategory(String categoryName) {
		solo.clickLongOnText(categoryName);
		solo.clickOnText("Delete");
	}

	@LargeTest
	public void test_delete_category_should_remove_category_from_list(){
		solo.clickOnActionBarItem(R.id.add_category);
		assertTrue("Create category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, TEST_DELETE_CATEGORY_TEXT);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
		solo.clickLongOnText(TEST_DELETE_CATEGORY_TEXT);
		assertTrue("Delete category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.clickOnText("Delete");
		assertFalse("Category was not deleted", solo.searchText(TEST_DELETE_CATEGORY_TEXT));
	}
	
	@LargeTest
	public void test_category_with_same_name_as_another_cant_be_added(){
		solo.clickOnActionBarItem(R.id.add_category);
		assertTrue("Create category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, TEST_ADD_CATEGORY_TEXT);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
		solo.clickOnActionBarItem(R.id.add_category);
		assertTrue("Create category dialog did not open",solo.waitForDialogToOpen(TIME_TO_WAIT_FOR_DIALOG));
		solo.enterText(0, TEST_ADD_CATEGORY_TEXT);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
		removeCreatedCategory(TEST_ADD_CATEGORY_TEXT);
		
		
		assertFalse("Possible to add more then one category with the same name", solo.searchText(TEST_ADD_CATEGORY_TEXT));
		
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

}
