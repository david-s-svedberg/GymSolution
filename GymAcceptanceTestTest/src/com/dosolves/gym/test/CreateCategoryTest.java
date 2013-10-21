package com.dosolves.gym.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.dosolves.gym.CategoryActivity;
import com.dosolves.gym.R;
import com.jayway.android.robotium.solo.Solo;

public class CreateCategoryTest extends ActivityInstrumentationTestCase2<CategoryActivity>{

	private static final String TEST_CATEGORY_TEXT = "Stomach";
	private static final int TIME_TO_WAIT_FOR_DIALOG = 5000;
	
	private Solo solo;
	
	public CreateCategoryTest(){
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
		solo.enterText(0, TEST_CATEGORY_TEXT);
		solo.clickOnButton("Ok");
		assertTrue("Create category dialog did not close", solo.waitForDialogToClose(TIME_TO_WAIT_FOR_DIALOG));
		assertTrue("Test category not found in list", solo.searchText(TEST_CATEGORY_TEXT));
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		solo.finishOpenedActivities();4
		
	}

}
