package com.dosolves.gym.app.gui.category;

import android.content.Context;

import com.dosolves.gym.app.gui.RequiredTextInputDialog;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemShouldBeCreatedCallback;
import com.dosolves.gym.domain.TextInputCallback;

public class CreateCategoryAlertDialog implements CreateItemDialogShower {

	private Context context;

	public CreateCategoryAlertDialog(Context context) {
		this.context = context;
	}

	@Override
	public void show(final ItemShouldBeCreatedCallback callback) {
		RequiredTextInputDialog dialog = new RequiredTextInputDialog(context, "New Category", "Enter name", new TextInputCallback() {
			
			@Override
			public void onTextInputDone(String value) {
				callback.onItemShouldBeCreated(value);
			}
		});
		dialog.show();		
	}
}
