package com.dosolves.gym.app.gui.performance.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.R;
import com.dosolves.gym.app.performance.gui.PerformanceActivity;
import com.dosolves.gym.domain.performance.NewSetShouldBeCreatedCallback;

@RunWith(RobolectricTestRunner.class)
public class PerformanceActivityTest {

	private static final double WEIGHT = 50.5;

	private static final int REPS = 12;
	
	@Mock
	NewSetShouldBeCreatedCallback callbackMock;
	
	PerformanceActivity sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = Robolectric.buildActivity(PerformanceActivity.class).create().get();;
		sut.setNewSetShouldBeCreatedCallback(callbackMock);
	}
	
	@Ignore("More of an integrationtest really")
	@Test
	public void click_enter_button_calls_callback(){
		Button enterButton = (Button)sut.findViewById(R.id.enterSetButton);
		EditText repsField = (EditText)sut.findViewById(R.id.repsInput);
		EditText weightField = (EditText)sut.findViewById(R.id.weightInput);
		
		repsField.setText(Integer.toString(REPS));
		weightField.setText(Double.toString(WEIGHT));
		
		enterButton.performClick();
		
		verify(callbackMock).onNewSetShouldBeCreated(REPS, WEIGHT);
	}
	
}
