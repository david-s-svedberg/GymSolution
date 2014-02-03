package com.dosolves.gym.domain.exercise.test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.widget.ArrayAdapter;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.CurrentCategoryHolder;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.exercise.ExerciseController;
import com.dosolves.gym.domain.exercise.ExerciseOpener;
import com.dosolves.gym.domain.exercise.data.ExerciseRetriever;
import com.dosolves.gym.domain.exercise.data.ExerciseUpdater;

@RunWith(RobolectricTestRunner.class)
public class ExerciseControllerTest {
	
	private static final String EXERCISE_NAME = "exerciseName";
	private static final int EXERCISE_ID = 345;
	private static final String CATEGORY_NAME = "CATEGORY_NAME";
	private static final int CATEGORY_ID = 52;
	private static final int POSITION = 5345;
	
	private static final String NEW_EXERCISE_NAME = "newExerciseName";
	
	@Mock
	ArrayAdapter<Exercise> adapterMock;
	@Mock
	ExerciseRetriever retrieverMock;
	@Mock
	CreateItemDialogShower createItemDialogShowerMock;
	@Mock
	ItemOptionMenuDialogShower itemOptionMenuDialogShowerMock; 
	@Mock
	ExerciseUpdater exerciseUpdaterMock;
	@Mock
	ExerciseOpener exerciseOpenerMock;
	@Mock
	CurrentCategoryHolder currentCategoryHolderMock;
	
	List<Exercise> exercisesMock;
	Category categoryMock;
	Exercise exerciseMock;
	
	ExerciseController sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		exercisesMock = new ArrayList<Exercise>();
		
		categoryMock = new Category(CATEGORY_ID, CATEGORY_NAME);
		exerciseMock = new Exercise(EXERCISE_ID, CATEGORY_ID, EXERCISE_NAME);
		
		sut = new ExerciseController(adapterMock, 
									 retrieverMock, 
									 createItemDialogShowerMock, 
									 exerciseUpdaterMock, 
									 itemOptionMenuDialogShowerMock,
									 exerciseOpenerMock,
									 currentCategoryHolderMock);
	}
	
	@Test
    public void onReadyToGetData_updates_exercises(){            
            
            when(currentCategoryHolderMock.getCurrentCategory()).thenReturn(categoryMock);
			when(retrieverMock.getExercisesInCategory(categoryMock)).thenReturn(exercisesMock);
            
            sut.onReadyToGetData();
            
            verifyExercisesHaveBeenUpdated();
    }
	
	@Test
	public void calls_exerciseUpdater_when_category_should_be_deleted(){
		when(adapterMock.getItem(POSITION)).thenReturn(exerciseMock);
		
		sut.onItemShouldBeDeleted(POSITION);
		verify(exerciseUpdaterMock).delete(exerciseMock);
	}
	
	
	@Test
	public void calls_exerciseUpdater_when_item_should_be_created(){
		when(currentCategoryHolderMock.getCurrentCategory()).thenReturn(categoryMock);
		
		sut.onItemShouldBeCreated(NEW_EXERCISE_NAME);
		
		verify(exerciseUpdaterMock).create(NEW_EXERCISE_NAME, CATEGORY_ID);		
	}
	
	@Test
	public void doesnt_create_new_exercise_if_another_with_same_name_already_exists(){
		exercisesMock.add(new Exercise(EXERCISE_ID,CATEGORY_ID,EXERCISE_NAME));
		
		when(currentCategoryHolderMock.getCurrentCategory()).thenReturn(categoryMock);
		when(retrieverMock.getExercisesInCategory(categoryMock)).thenReturn(exercisesMock);
		
		sut.onItemShouldBeCreated(EXERCISE_NAME);
		
		verifyZeroInteractions(exerciseUpdaterMock);
	}

	@Test
	public void calls_exerciseOpener_when_exercise_have_been_clicked(){
		when(adapterMock.getItem(POSITION)).thenReturn(exerciseMock);
		
		sut.onOpenItemRequested(POSITION);
		
		verify(exerciseOpenerMock).openExercise(exerciseMock);
	}

	private void verifyExercisesHaveBeenUpdated() {
		verify(adapterMock).clear();
		verify(adapterMock).addAll(exercisesMock);
		verify(adapterMock).notifyDataSetChanged();
	}

}
