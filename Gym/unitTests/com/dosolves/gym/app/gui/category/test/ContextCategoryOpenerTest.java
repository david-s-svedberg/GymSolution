package com.dosolves.gym.app.gui.category.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.category.CategoriesActivity;
import com.dosolves.gym.app.gui.category.ContextCategoryOpener;
import com.dosolves.gym.app.gui.exercise.ExercisesActivity;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryOpener;

@RunWith(RobolectricTestRunner.class)
public class ContextCategoryOpenerTest {

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
				if (intentContainsCategory(categoryMock, intent) && intent.getActivityName.equals(ExercisesActivity.class.getName() && intent.getContext().isSameAs(contextMock)))
					return true;
				else
					return false;
			}

			private boolean intentContainsCategory(final Category categoryMock, Intent intent) {
				return intent.getExtras().get(ExercisesActivity.CATEGORY_BUNDLE_KEY).equals(categoryMock);
			}
			
		}));
	}
	
}
