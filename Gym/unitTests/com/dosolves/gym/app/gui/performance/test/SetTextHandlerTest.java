package com.dosolves.gym.app.gui.performance.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.app.performance.gui.SetTextHandler;
import com.dosolves.gym.app.performance.gui.SetTextHandlerImpl;

@RunWith(RobolectricTestRunner.class)
public class SetTextHandlerTest {

	SetTextHandler sut;
	
	@Mock
	Button buttonMock;
	@Mock
	EditText repsInputMock;
	@Mock
	EditText weightInputMock;
	@Mock
	Editable repsEditableMock;
	@Mock
	Editable weightEditableMock;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new SetTextHandlerImpl(buttonMock, repsInputMock, weightInputMock);
	}
	
	@Test
	public void no_reps_text_disables_button(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(weightInputMock.getText()).thenReturn(weightEditableMock);
		when(repsEditableMock.toString()).thenReturn("");
		when(weightEditableMock.toString()).thenReturn("50");
		
		sut.afterTextChanged(null);
		
		verify(buttonMock).setEnabled(false);
	}
	
	@Test
	public void no_weight_text_enables_button(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(weightInputMock.getText()).thenReturn(weightEditableMock);
		when(repsEditableMock.toString()).thenReturn("12");
		when(weightEditableMock.toString()).thenReturn("");
		
		sut.afterTextChanged(null);
		
		verify(buttonMock).setEnabled(true);
	}
	
	@Test
	public void weight_input_of_three_decimals_is_shortened_to_two(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(weightInputMock.getText()).thenReturn(weightEditableMock);
		when(repsEditableMock.toString()).thenReturn("12");
		
		
		when(weightEditableMock.toString()).thenReturn("50.555");
		
		sut.afterTextChanged(null);
		
		verify(weightInputMock).setText("50.55");
	}
	
	@Test
	public void increment_works_As_expected(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(repsEditableMock.toString()).thenReturn("12");
		
		sut.incrementRepsText();
		
		verify(repsInputMock).setText("13");
	}
	
	@Test
	public void decrement_works_As_expected(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(repsEditableMock.toString()).thenReturn("12");
		
		sut.decrementRepsText();
		
		verify(repsInputMock).setText("11");
	}
	
	@Test
	public void decrement_from_one_stays_on_one(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(repsEditableMock.toString()).thenReturn("1");
		
		sut.decrementRepsText();
		
		verify(repsInputMock, never()).setText(anyString());
	}
	
	@Test
	public void increment_from_nothing_sets_one(){
		when(repsInputMock.getText()).thenReturn(repsEditableMock);
		when(repsEditableMock.toString()).thenReturn("");
		
		sut.incrementRepsText();
		
		verify(repsInputMock).setText("1");
	}
}
