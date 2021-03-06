package com.dosolves.gym.app.gui;

import android.content.Context;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemShouldBeCreatedCallback;
import com.dosolves.gym.domain.TextInputCallback;

public class CreateItemAlertDialogShower implements CreateItemDialogShower {

	private Context context;
	private String title;

	public CreateItemAlertDialogShower(Context context, String title) {
		this.context = context;
		this.title = title;
	}

	@Override
	public void show(final ItemShouldBeCreatedCallback callback) {
		RequiredTextInputDialog dialog = new RequiredTextInputDialog(context, this.title, context.getString(R.string.enter_name), new TextInputCallback() {
			
			@Override
			public void onTextInputDone(String value) {
				callback.onItemShouldBeCreated(value);
			}
			
		});
		dialog.show(null);		
	}
}
