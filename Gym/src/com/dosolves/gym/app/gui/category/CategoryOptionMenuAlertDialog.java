package com.dosolves.gym.app.gui.category;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.ItemShouldBeDeletedCallback;

public class CategoryOptionMenuAlertDialog implements ItemOptionMenuDialogShower{

	private Context context;

	public CategoryOptionMenuAlertDialog(Context context){
		this.context = context;
		
	}

	@Override
	public void show(final int itemPosition, final ItemShouldBeDeletedCallback callback) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		
		alert.setItems(new String[]{"Delete"},new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callback.onItemShouldBeDeleted(itemPosition);
			}
			
		});
		
		alert.show();
	}

}
