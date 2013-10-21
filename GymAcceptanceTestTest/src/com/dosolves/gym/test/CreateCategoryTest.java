package com.dosolves.gym.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.dosolves.gym.CategoryActivity;
import com.dosolves.gym.R;

public class CreateCategoryTest extends ActivityInstrumentationTestCase2<CategoryActivity>{

	private CategoryActivity activityUnderTest;
	
	public CreateCategoryTest(){
		super(CategoryActivity.class);
	}
	
	public CreateCategoryTest(Class<CategoryActivity> activityClass) {
		super(activityClass);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        activityUnderTest = getActivity();     
    }
	
	@SmallTest
	public void testPreconditions() {
	    assertNotNull(activityUnderTest);	    
	}
	
	@MediumTest
	public void test_Adding_new_category_shoud_add_category_to_list(){
		boolean menuItemFound = getInstrumentation().invokeMenuActionSync(activityUnderTest,R.id.add_category , 0);
		assertTrue("Menuitem 'Add category' not found",menuItemFound);
	}

}
