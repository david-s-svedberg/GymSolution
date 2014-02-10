package com.dosolves.gym.app.performance.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;

public class EditSetFragmentDialogShower implements EditSetDialogShower {

	private Context context;

	public EditSetFragmentDialogShower(Context context) {
		this.context = context;

	}

	@Override
	public void show(final Set set, final SetShouldBeEditedCallback editCallback) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(R.string.edit_set);
		
		View dialogView = View.inflate(context, R.layout.edit_set_dialog, null);
		alert.setView(dialogView);
		
		final EditText repsInput = (EditText)dialogView.findViewById(R.id.repsInput);
		final EditText weightInput = (EditText)dialogView.findViewById(R.id.weightInput);
		final Button saveButton = (Button)dialogView.findViewById(R.id.save_button);
		final Button cancelButton = (Button)dialogView.findViewById(R.id.cancel_button);
		
		final AlertDialog d = alert.create();
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int reps = Integer.parseInt(repsInput.getText().toString());
				double weight = Double.parseDouble(weightInput.getText().toString());
				editCallback.onSetShouldBeUpdated(set, reps, weight);
				d.dismiss();
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				d.dismiss();
			}
		});
	
		TextWatcher textWatcher = new TextWatcher() {		    
		    @Override
		    public void afterTextChanged(Editable arg0) {
		    	saveButton.setEnabled(repsHasValidValue(repsInput) && weightHasValidValue(weightInput));
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		};
		repsInput.addTextChangedListener(textWatcher);
		weightInput.addTextChangedListener(textWatcher);
		
		// and disable the button to start with
		saveButton.setEnabled(false);
		d.show();
	}
 
	protected boolean weightHasValidValue(EditText weightInput) {
		return weightInput.getText().length() > 0 && isDouble(weightInput.getText().toString()) && doubleIsMoreThenZero(weightInput.getText().toString());
	}

	private boolean doubleIsMoreThenZero(String value) {
		return Double.parseDouble(value) > 0.0;
	}

	private static boolean isDouble(String value) {
		boolean ret = true;
		try{
			Double.parseDouble(value);
		}
		catch(NumberFormatException e){
			ret = false;
		}
		return ret;
	}

	protected boolean repsHasValidValue(EditText repsInput) {
		return repsInput.getText().length() > 0 && isInt(repsInput.getText().toString()) && intIsMoreThenZero(repsInput.getText().toString());
	}

	private boolean intIsMoreThenZero(String value) {
		return Integer.parseInt(value) > 0;
	}

	private static boolean isInt(String value) {
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
