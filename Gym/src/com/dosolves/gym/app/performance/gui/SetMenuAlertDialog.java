package com.dosolves.gym.app.performance.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.performance.EditSetDialogRequestedCallback;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetMenuDialogShower;
import com.dosolves.gym.domain.performance.SetShouldBeDeletedCallback;

public class SetMenuAlertDialog implements SetMenuDialogShower {

	private static final int DELETE_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	
	private Context context;
	
	public SetMenuAlertDialog(Context context) {
		this.context = context;
	}

	@Override
	public void show(final Set set, final SetShouldBeDeletedCallback deleteCallback, final EditSetDialogRequestedCallback showEditCallback) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		
		String[] optionItems = new String[2];
		
		optionItems[DELETE_INDEX] = context.getString(R.string.delete);
		optionItems[EDIT_INDEX] = context.getString(R.string.edit);
		
		alert.setItems(optionItems,new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int index) {
				switch(index){
				case DELETE_INDEX:
					deleteCallback.onSetShouldBeDeleted(set);
					break;
				case EDIT_INDEX:
					showEditCallback.onEditSetDialogRequested(set);
					break;
				default:
					throw new IllegalArgumentException(String.format("index %d not handled", index));
				}
				
			}
			
		});
		
		alert.show();
	}

}
