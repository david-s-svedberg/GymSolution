package com.dosolves.gym.app.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.dosolves.gym.R;
import com.dosolves.gym.app.ads.RouterActivity;
import com.dosolves.gym.domain.UserResponseListener;

public class YesNoDialog extends DialogFragment {

	public static final String TITLE_KEY = "TITLE_KEY";
	public static final String MESSAGE_KEY = "MESSAGE_KEY";
	
	private UserResponseListener userResponseListener;
	
	private int titleId = -1;
	private int messageId = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userResponseListener = extractUserResponseListener();
		titleId = extractTitleId();
		messageId = extractMessageId();
	}
	
	private int extractMessageId() {
		return getArguments().getInt(MESSAGE_KEY);
	}

	private int extractTitleId() {
		return getArguments().getInt(TITLE_KEY);
	}

	private UserResponseListener extractUserResponseListener() {
		return (UserResponseListener)((RouterActivity)getActivity()).getDialogResultListener();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(titleId);
		builder.setMessage(messageId);
		
		builder.setPositiveButton(R.string.yes, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				userResponseListener.yes();
				closeRouterActivity();
			}
			
		});
		
		builder.setNegativeButton(R.string.no, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				userResponseListener.no();
				closeRouterActivity();
			}
			
		});
		
		return builder.create();
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		closeRouterActivity();
	}

	protected void closeRouterActivity() {
		getActivity().finish();
	}
	
}
