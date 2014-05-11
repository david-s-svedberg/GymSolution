package com.dosolves.gym.app.performance.gui;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class SetTextHandler extends AbstractTextWatcher {

	private Button proceedButton;
	private EditText repsInput;
	private EditText weightInput;

	public SetTextHandler(Button proceedButton, EditText repsInput, EditText weightInput){
		this.proceedButton = proceedButton;
		this.repsInput = repsInput;
		this.weightInput = weightInput;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		setEnabledStateForProceedButton();
		removeThirdOrMoreDecimalDigitOnWeight();
	}

	private void removeThirdOrMoreDecimalDigitOnWeight() {
		if(weightHasMoreThenTwoDecimalDigits()){
			weightInput.setText(getWeightTrimmedToTwoDecimalDigits());
			weightInput.setSelection(getWeightString().length());
		}
	}

	private String getWeightTrimmedToTwoDecimalDigits() {
		double shiftedWeight = getWeight()*100;
		int removeDecimals = (int)shiftedWeight;
		double trimmedWeight = ((double)removeDecimals)/100;
		return Double.toString(trimmedWeight);
	}

	private boolean weightHasMoreThenTwoDecimalDigits() {
		
		String weight = getWeightString();
		
		return weightHasValue(weight) && moreThan2DecimalPlaces(getWeight());
	}
	
	private boolean moreThan2DecimalPlaces(double value)
	{
	    double shiftedValue = value * 100;
	    return shiftedValue != Math.floor(shiftedValue);
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
