package com.dosolves.gym.app.gui.category;

import com.dosolves.gym.app.gui.RequiredTextInputDialog;
import com.dosolves.gym.domain.TextInputCallback;
import com.dosolves.gym.domain.category.CategoryShouldBeCreatedCallback;
import com.dosolves.gym.domain.category.CreateCategoryDialog;

import android.content.Context;

public class CreateCategoryAlertDialog implements CreateCategoryDialog {

	private Context context;

	public CreateCategoryAlertDialog(Context context) {
		this.context = context;
	}

	@Override
	public void show(final CategoryShouldBeCreatedCallback callback) {
		RequiredTextInputDialog dialog = new RequiredTextInputDialog(context, "New Category", "Enter name", new TextInputCallback() {
			
			@Override
			public void onTextInputDone(String value) {
				callback.onCategoryShouldBeCreated(value);
			}
		});
		dialog.show();
	}
}
