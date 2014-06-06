package com.dosolves.gym.domain.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dosolves.gym.domain.AddDefaultExercisesUseCase;
import com.dosolves.gym.domain.AddDefaultExercisesUseCaseImpl;
import com.dosolves.gym.domain.TemplateDataHolder;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserNotifier;
import com.dosolves.gym.domain.UserResponseListener;
import com.dosolves.gym.domain.category.CategoryTemplate;
import com.dosolves.gym.domain.category.data.CategoryCreator;
import com.dosolves.gym.domain.exercise.ExerciseTemplate;
import com.dosolves.gym.domain.exercise.data.ExerciseCreator;

public class AddDefaultExercisesUseCaseTest {

	private static final int CATEGORY_ID2 = 87689;
	private static final int CATEGORY_ID1 = 2354;
	private static final String EXERCISE_NAME2 = "ExerciseName2";
	private static final String EXERCISE_NAME1 = "ExerciseName1";
	private static final String CATEGORY_NAME2 = "CategoryName2";
	private static final String CATEGORY_NAME1 = "CategoryName1";
	
	AddDefaultExercisesUseCase sut;
	
	@Mock
	UserAsker userAskerMock;
	@Mock
	TemplateDataHolder templateDataMock;
	@Mock
	CategoryCreator categoryCreatorMock;
	@Mock
	ExerciseCreator exerciseCreatorMock;
	@Mock
	UserNotifier userNotifierMock;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new AddDefaultExercisesUseCaseImpl(userAskerMock,
												 templateDataMock,
												 categoryCreatorMock,
												 exerciseCreatorMock,
												 userNotifierMock);		
	}
	
	@Test
	public void asks_user_on_first_start_up(){
		sut.runUseCase();
		
		verify(userAskerMock).askUser(any(UserResponseListener.class));
	}
	
	@Test
	public void if_yes_gets_templete_data(){
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener userResponseListener = (UserResponseListener)invocation.getArguments()[0];
				userResponseListener.yes();
				return null;
			}
		}).when(userAskerMock).askUser(any(UserResponseListener.class));
		
		sut.runUseCase();
		
		verify(templateDataMock).getTemplateData();
	}
	
	@Test
	public void created_one_category_from_each_template(){
		List<CategoryTemplate> templateCategories = new ArrayList<CategoryTemplate>();
		templateCategories.add(new CategoryTemplate(CATEGORY_NAME1));
		templateCategories.add(new CategoryTemplate(CATEGORY_NAME2));
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener userResponseListener = (UserResponseListener)invocation.getArguments()[0];
				userResponseListener.yes();
				return null;
			}
		}).when(userAskerMock).askUser(any(UserResponseListener.class));
		
		when(templateDataMock.getTemplateData()).thenReturn(templateCategories);
		
		sut.runUseCase();
		
		verify(categoryCreatorMock).create(CATEGORY_NAME1);
		verify(categoryCreatorMock).create(CATEGORY_NAME2);
	}
	
	@Test
	public void creates_exercises_from_each_template(){
		List<CategoryTemplate> templateCategories = new ArrayList<CategoryTemplate>();
		CategoryTemplate categoryTemplate1 = new CategoryTemplate(CATEGORY_NAME1);
		CategoryTemplate categoryTemplate2 = new CategoryTemplate(CATEGORY_NAME2);
		categoryTemplate1.addExercise(new ExerciseTemplate(EXERCISE_NAME1));
		categoryTemplate2.addExercise(new ExerciseTemplate(EXERCISE_NAME2));
		templateCategories.add(categoryTemplate1);
		templateCategories.add(categoryTemplate2);
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener userResponseListener = (UserResponseListener)invocation.getArguments()[0];
				userResponseListener.yes();
				return null;
			}
		}).when(userAskerMock).askUser(any(UserResponseListener.class));
		
		when(templateDataMock.getTemplateData()).thenReturn(templateCategories);
		when(categoryCreatorMock.create(CATEGORY_NAME1)).thenReturn(CATEGORY_ID1);
		when(categoryCreatorMock.create(CATEGORY_NAME2)).thenReturn(CATEGORY_ID2);
		
		sut.runUseCase();
		
		verify(exerciseCreatorMock).create(EXERCISE_NAME1, CATEGORY_ID1);
		verify(exerciseCreatorMock).create(EXERCISE_NAME2, CATEGORY_ID2);
	}
	
	@Test
	public void notifies_user_that_Default_exercises_have_been_created(){
		List<CategoryTemplate> templateCategories = new ArrayList<CategoryTemplate>();
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener userResponseListener = (UserResponseListener)invocation.getArguments()[0];
				userResponseListener.yes();
				return null;
			}
		}).when(userAskerMock).askUser(any(UserResponseListener.class));
		
		when(templateDataMock.getTemplateData()).thenReturn(templateCategories);
		
		sut.runUseCase();
		
		verify(userNotifierMock).notifyThatDefaultExercisesHaveBeenCreated();
	}
	
}
