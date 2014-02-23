package com.dosolves.gym.app.gui;

import com.dosolves.gym.domain.TextInputCallback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class RequiredTextInputDialog{
	
	private String title;
	private String message;
	private Context context;
	private TextInputCallback callback;
	
	public RequiredTextInputDialog(Context context, String title, String message, TextInputCallback callback){
		this.context = context;
		this.title = title;
		this.message = message;
		this.callback = callback;			
	}

	public void show(String presetValue) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
	
		// Set an EditText view to get user input 
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		input.setText(presetValue);
		input.selectAll();
		alert.setView(input);
	
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  callback.onTextInputDone(value);
			}
		});
	
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		final AlertDialog d = alert.create();
		
		input.addTextChangedListener(new TextWatcher() {
		    private void handleText() {
		        // Grab the button
		        final Button okButton = d.getButton(AlertDialog.BUTTON_POSITIVE);
		        if(input.getText().length() == 0) {
		            okButton.setEnabled(false);
		        } else {
		            okButton.setEnabled(true);
		        }
		    }
		    @Override
		    public void afterTextChanged(Editable arg0) {
		        handleText();
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		        // Nothing to do
		    }
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		       // Nothing to do
		    }
		});
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});

		d.show();
		// and disable the button to start with
		d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
		input.requestFocus();
	}
}