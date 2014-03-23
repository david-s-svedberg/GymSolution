package com.dosolves.gym.ads.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;

import com.dosolves.gym.ads.ViewSetter;
import com.dosolves.gym.app.gui.CategoryAndExerciseViewSetter;
import com.dosolves.gym.app.gui.PerformaceViewSetter;

public class ViewSetterTest {
	
	@Mock
	Activity activityMock;
	
	ViewSetter sutPerformance;
	ViewSetter sutCategoryAndExercise;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		 sutPerformance = new PerformaceViewSetter(activityMock);
		 sutCategoryAndExercise = new CategoryAndExerciseViewSetter(activityMock);
	}
	
	@Test
	public void sets_correct_content_view_for_ads_performace(){
		sutPerformance.setAdsView();
		
		verify(activityMock).setContentView(com.dosolves.gym.R.layout.activity_exercise_input_with_ads);
	}
	
	@Test
	public void sets_correct_content_view_for_ads_category_and_exercise(){
		sutCategoryAndExercise.setAdsView();
		
		verify(activityMock).setContentView(com.dosolves.gym.R.layout.user_updateable_items_with_ads);
	}
	
	@Test
	public void sets_correct_content_view_for_no_ads_performace(){
		sutPerformance.setAdsFreeView();
		
		verify(activityMock).setContentView(com.dosolves.gym.R.layout.activity_exercise_input);
	}
	
	@Test
	public void sets_no_content_for_no_ads_category_and_exercise(){
		sutCategoryAndExercise.setAdsFreeView();
		
		verifyZeroInteractions(activityMock);
	}
}

