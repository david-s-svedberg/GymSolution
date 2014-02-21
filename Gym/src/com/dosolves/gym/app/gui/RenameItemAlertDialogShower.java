package com.dosolves.gym.app.gui;

import android.content.Context;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.ItemShouldBeRenamedCallback;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.TextInputCallback;

public class RenameItemAlertDialogShower implements RenameDialogShower {

	private Context context;
	private String title;

	public RenameItemAlertDialogShower(Context context, String title) {
		this.context = context;
		this.title = title;
	}

	@Override
	public void show(final int position, final ItemShouldBeRenamedCallback callback) {
		RequiredTextInputDialog dialog = new RequiredTextInputDialog(context, this.title, context.getString(R.string.enter_new_name), new TextInputCallback() {
			
			@Override
			public void onTextInputDone(String value) {
				callback.onItemShouldBeRenamed(position, value);
			}
		});
		dialog.show(null);
	}

}
