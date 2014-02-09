package com.dosolves.gym.app.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.ItemShouldBeDeletedCallback;

public class ItemOptionMenuAlertDialogShower implements ItemOptionMenuDialogShower{

	private static final int RENAME_INDEX = 1;
	private static final int DELETE_INDEX = 0;
	private Context context;

	public ItemOptionMenuAlertDialogShower(Context context){
		this.context = context;
		
	}

	@Override
	public void show(final int itemPosition, final ItemShouldBeDeletedCallback deleteCallback, final RenameDialogRequestedCallback renameCallback) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		
		String[] optionItems = new String[2];
		
		optionItems[DELETE_INDEX] = context.getString(R.string.delete);
		optionItems[RENAME_INDEX] = context.getString(R.string.rename);
		
		alert.setItems(optionItems,new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int index) {
				switch(index){
				case DELETE_INDEX:
					deleteCallback.onItemShouldBeDeleted(itemPosition);
					break;
				case RENAME_INDEX:
					renameCallback.onRenameDialogRequested(itemPosition);
					break;
				default:
					throw new IllegalArgumentException(String.format("index %d not handled", index));
				}
				
			}
			
		});
		
		alert.show();
	}

}
