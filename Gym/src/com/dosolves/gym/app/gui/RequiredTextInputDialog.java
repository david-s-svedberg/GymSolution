package com.dosolves.gym.app.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.dosolves.gym.R;
import com.dosolves.gym.app.performance.gui.AbstractTextWatcher;
import com.dosolves.gym.domain.TextInputCallback;

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
	
		alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  callback.onTextInputDone(value);
			}
		});
	
		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});
		
		final AlertDialog d = alert.create();
		
		input.addTextChangedListener(new AbstractTextWatcher() {
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