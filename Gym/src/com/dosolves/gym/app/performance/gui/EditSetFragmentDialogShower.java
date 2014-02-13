package com.dosolves.gym.app.performance.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;

public class EditSetFragmentDialogShower implements EditSetDialogShower {

	public static final String SET_KEY = "setKey";
	
	private FragmentManagerProvider fragmentManagerProvider;

	public EditSetFragmentDialogShower(FragmentManagerProvider fragmentManagerProvider) {
		this.fragmentManagerProvider = fragmentManagerProvider;
	}

	@Override
	public void show(final Set set, final SetShouldBeEditedCallback editCallback) {
		EditSetFragmentDialogShowerInner dialog = new EditSetFragmentDialogShowerInner();
		Bundle arguments = new Bundle();
		arguments.putSerializable(SET_KEY, set);
		
		dialog.setArguments(arguments);
		dialog.show(fragmentManagerProvider.getFragmentManager(), "Tag");
	}
 	
	public static class EditSetFragmentDialogShowerInner extends DialogFragment{
		
		private Set set;
		private SetShouldBeEditedCallback editCallback;

		@Override
		public void onResume() {
			super.onResume();
			set = (Set) getArguments().getSerializable(EditSetFragmentDialogShower.SET_KEY);
			editCallback= (SetShouldBeEditedCallback)getActivity();
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.edit_set);
			
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View dialogView = inflater.inflate(R.layout.edit_set_dialog, null);
			builder.setView(dialogView);
			
			final EditText repsInput = (EditText)dialogView.findViewById(R.id.repsInput);
			final EditText weightInput = (EditText)dialogView.findViewById(R.id.weightInput);
			final Button saveButton = (Button)dialogView.findViewById(R.id.save_button);
			final Button cancelButton = (Button)dialogView.findViewById(R.id.cancel_button);
			
			saveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					int reps = Integer.parseInt(repsInput.getText().toString());
					double weight = Double.parseDouble(weightInput.getText().toString());
					editCallback.onSetShouldBeUpdated(set, reps, weight);
					EditSetFragmentDialogShowerInner.this.dismiss();
				}
			});
			
			cancelButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					EditSetFragmentDialogShowerInner.this.dismiss();
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
			return builder.create();
		}
		

		protected boolean weightHasValidValue(EditText weightInput) {
			return weightInput.getText().length() > 0 && isDouble(weightInput.getText().toString()) && doubleIsMoreThenZero(weightInput.getText().toString());
		}

		private boolean doubleIsMoreThenZero(String value) {
			return Double.parseDouble(value) > 0.0;
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

		protected boolean repsHasValidValue(EditText repsInput) {
			return repsInput.getText().length() > 0 && isInt(repsInput.getText().toString()) && intIsMoreThenZero(repsInput.getText().toString());
		}

		private boolean intIsMoreThenZero(String value) {
			return Integer.parseInt(value) > 0;
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
}
