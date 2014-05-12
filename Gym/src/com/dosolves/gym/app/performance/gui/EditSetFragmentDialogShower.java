package com.dosolves.gym.app.performance.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.domain.performance.EditSetDialogShower;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;
import com.dosolves.gym.utils.StringUtils;

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
		private SetTextHandler setTextHandler;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
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
			
			repsInput.setText(Integer.toString(set.getReps()));
			weightInput.setText(StringUtils.doubleToStringRemoveTrailingZeros(set.getWeight()));
			
			saveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					editCallback.onSetShouldBeUpdated(set, setTextHandler.getReps(), setTextHandler.getWeight());
					EditSetFragmentDialogShowerInner.this.dismiss();
				}

			});
			
			cancelButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					EditSetFragmentDialogShowerInner.this.dismiss();
				}
			});
		
			setTextHandler = new SetTextHandlerImpl(saveButton,repsInput,weightInput);
			
			repsInput.addTextChangedListener(setTextHandler);
			weightInput.addTextChangedListener(setTextHandler);
			
			// and disable the button to start with
			saveButton.setEnabled(false);
			return builder.create();
		}

	}
}
