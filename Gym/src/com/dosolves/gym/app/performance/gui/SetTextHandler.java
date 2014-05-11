package com.dosolves.gym.app.performance.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class SetTextHandler extends AbstractTextWatcher {

	private Button proceedButton;
	private EditText repsInput;
	private EditText weightInput;
	private boolean reentrantCheck = false;
	private Pattern moreThenTwoDecimalsPattern;
	private Pattern trimmedToTwoDecimalsPattern;

	public SetTextHandler(Button proceedButton, EditText repsInput, EditText weightInput){
		this.proceedButton = proceedButton;
		this.repsInput = repsInput;
		this.weightInput = weightInput;
		moreThenTwoDecimalsPattern = Pattern.compile("^\\d*\\.\\d{3,}$");
		trimmedToTwoDecimalsPattern = Pattern.compile("(\\d*\\.\\d{2})");
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		setEnabledStateForProceedButton();
		removeThirdOrMoreDecimalDigitOnWeight();
	}

	private void removeThirdOrMoreDecimalDigitOnWeight() {
		if(!reentrantCheck){
			reentrantCheck = true;
			if(weightHasMoreThenTwoDecimalDigits()){
				int cursorBefore = weightInput.getSelectionEnd();
				int cursorAfter = cursorBefore == getWeightString().length() ? cursorBefore -1 : cursorBefore;
				weightInput.setText(getWeightTrimmedToTwoDecimalDigits());
				weightInput.setSelection(cursorAfter);
			}
			reentrantCheck = false;
		}
	}

	private String getWeightTrimmedToTwoDecimalDigits() {
		String weightString = getWeightString();
		Matcher matcher = trimmedToTwoDecimalsPattern.matcher(weightString);
		matcher.find();
		return matcher.group();
//		
//		int removeDecimals = (int)Math.round(getWeight()*100.0);
//		double trimmedWeight = ((double)removeDecimals)/100;
//		return Double.toString(trimmedWeight);
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
	
	protected boolean repsHasValidValue() {
		return repsInput.getText().length() > 0 && isInt(repsInput.getText().toString()) && intIsMoreThenZero(repsInput.getText().toString());
	}

	private boolean intIsMoreThenZero(String value) {
		return Integer.parseInt(value) > 0;
	}
	
	protected boolean weightHasValidValue() {
		String weight = getWeightString();
		return noWeightInput(weight)  || validWeightInput(weight);
	}

	private boolean validWeightInput(String weight) {
		return weight.length() > 0 && isDouble(weight) && doubleIsEqualOrMoreThenZero(weight);
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

	protected double getWeight() {
		double ret = 0.0;
		String weight = weightInput.getText().toString();
		try{
			ret = Double.parseDouble(weight);
		}
		catch(NumberFormatException e){}
		
		return ret;
	}

	public int getReps() {
		return Integer.parseInt(repsInput.getText().toString());
	}
	
	protected void decrementRepsText() {
		if(repsHasValidValue()) {
			int reps = getReps();
			if(reps > 1)
				repsInput.setText(Integer.toString(reps-1));
		}		
	}

	protected void incrementRepsText() {
		if(repsHasValidValue())
			repsInput.setText(Integer.toString(getReps()+1));
		else
			repsInput.setText(Integer.toString(1));
	}
	
}
