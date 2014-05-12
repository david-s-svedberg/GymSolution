package com.dosolves.gym.app.performance.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class SetTextHandlerImpl extends AbstractTextWatcher implements SetTextHandler {

	private static final String TRIMM_TO_TWO_DECIMALS_PATTERN = "(\\d*\\.\\d{2})";
	private static final String MORE_THEN_TWO_DECIAMLS_PATTERN = "^\\d*\\.\\d{3,}$";
	
	private Button proceedButton;
	private EditText repsInput;
	private EditText weightInput;
	private Pattern moreThenTwoDecimalsPattern;
	private Pattern trimmedToTwoDecimalsPattern;

	public SetTextHandlerImpl(Button proceedButton, EditText repsInput, EditText weightInput){
		this.proceedButton = proceedButton;
		this.repsInput = repsInput;
		this.weightInput = weightInput;
		
		moreThenTwoDecimalsPattern = Pattern.compile(MORE_THEN_TWO_DECIAMLS_PATTERN);
		trimmedToTwoDecimalsPattern = Pattern.compile(TRIMM_TO_TWO_DECIMALS_PATTERN);
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		setEnabledStateForProceedButton();
		removeThirdOrMoreDecimalDigitOnWeight();
	}
	
	@Override
	public double getWeight() {
		double ret = 0.0;
		String weight = weightInput.getText().toString();
		try{
			ret = Double.parseDouble(weight);
		}
		catch(NumberFormatException e){}
		
		return ret;
	}

	@Override
	public int getReps() {
		return Integer.parseInt(repsInput.getText().toString());
	}
	
	@Override
	public void decrementRepsText() {
		if(repsHasValidValue()) {
			int reps = getReps();
			if(reps > 1)
				repsInput.setText(Integer.toString(reps-1));
		}		
	}

	@Override
	public void incrementRepsText() {
		if(repsHasValidValue())
			repsInput.setText(Integer.toString(getReps()+1));
		else
			repsInput.setText(Integer.toString(1));
	}

	private boolean reentrantCheck = false;
	
	private void removeThirdOrMoreDecimalDigitOnWeight() {
		if(!reentrantCheck){
			reentrantCheck = true;
			if(weightHasMoreThenTwoDecimalDigits()){
				int cursorPositionBefore = weightInput.getSelectionEnd();
				int cursorPositionToSetAfterEdit = cursorAtEndOfWeightInput(cursorPositionBefore) ? cursorPositionBefore -1 : cursorPositionBefore;
				
				weightInput.setText(getWeightTrimmedToTwoDecimalDigits());
				weightInput.setSelection(cursorPositionToSetAfterEdit);
			}
			reentrantCheck = false;
		}
	}

	private boolean cursorAtEndOfWeightInput(int cursorPositionBefore) {
		return cursorPositionBefore == getWeightString().length();
	}

	private String getWeightTrimmedToTwoDecimalDigits() {
		String weightString = getWeightString();
		Matcher matcher = trimmedToTwoDecimalsPattern.matcher(weightString);
		matcher.find();
		return matcher.group();
	}
	
	private boolean weightHasMoreThenTwoDecimalDigits() {
		String weight = getWeightString();
		
		return weightHasValue(weight) && moreThan2DecimalPlaces(weight);
	}
	
	private boolean moreThan2DecimalPlaces(String weight)
	{
		return moreThenTwoDecimalsPattern.matcher(weight).matches();	    
	}

	private boolean weightHasValue(String weight) {
		return !noWeightInput(weight);
	}

	private String getWeightString() {
		return weightInput.getText().toString();
	}

	private void setEnabledStateForProceedButton() {
		proceedButton.setEnabled(repsHasValidValue() && weightHasValidValue());
	}
	
	private boolean repsHasValidValue() {
		String reps = repsInput.getText().toString();
		return repsHasValue(reps) && isInt(reps) && intIsMoreThenZero(reps);
	}

	private boolean repsHasValue(String reps) {
		return reps.length() > 0;
	}

	private boolean intIsMoreThenZero(String value) {
		return Integer.parseInt(value) > 0;
	}
	
	private boolean weightHasValidValue() {
		String weight = getWeightString();
		return noWeightInput(weight) || validWeightInput(weight);
	}

	private boolean validWeightInput(String weight) {
		return repsHasValue(weight) && isDouble(weight) && doubleIsEqualOrMoreThenZero(weight);
	}

	private boolean noWeightInput(String weight) {
		return weight.length() == 0;
	}

	private boolean doubleIsEqualOrMoreThenZero(String value) {
		return Double.parseDouble(value) >= 0.0;
	}

	private boolean isDouble(String value) {
		boolean ret = true;
		try{
			Double.parseDouble(value);
		}
		catch(NumberFormatException e){
			ret = false;
		}
		return ret;
	}

	private boolean isInt(String value) {
		boolean ret = true;
		try{
			Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			ret = false;
		}
		return ret;
	}

	
	
}
