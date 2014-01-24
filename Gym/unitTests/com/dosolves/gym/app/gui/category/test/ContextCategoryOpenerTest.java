package com.dosolves.gym.app.gui.category.test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;

import com.dosolves.gym.app.gui.category.ContextCategoryOpener;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryOpener;

@RunWith(RobolectricTestRunner.class)
public class ContextCategoryOpenerTest extends AndroidTestCase {

	private static final String CATEGORY_NAME = "categoryName";
	private static final int CATEGORY_ID = 234;
	
	@Mock
	Context contextMock;
	
	CategoryOpener sut;
	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ContextCategoryOpener(contextMock);
	}
	
	@Test
	public void calls_addCategoryRequestedCallback_when_add_category_menu_item_is_selected(){
		
		final Category categoryMock = new Category(CATEGORY_ID, CATEGORY_NAME);
		sut.openCategory(categoryMock);
		
		verify(contextMock).startActivity(argThat(new ArgumentMatcher<Intent>(){

			@Override
			public boolean matches(Object argument) {
				Intent intent = (Intent) argument;
				if (intentContainsCategory(categoryMock, intent) && intentTargetsExercisesActivity(intent))
					return true;
				else
					return false;
			}

			private boolean intentTargetsExercisesActivity(Intent intent) {
				assertTrue(intent.getComponent().getClassName().equals(ExercisesActivity.class.getName()));
				return true;
			}

			private boolean intentContainsCategory(final Category categoryMock, Intent intent) {
				Category category = (Category)intent.getExtras().get(ExercisesActivity.CATEGORY_BUNDLE_KEY);
				assertTrue(category.equals(categoryMock));
				return true;
			}
			
		}));
	}
	
}
