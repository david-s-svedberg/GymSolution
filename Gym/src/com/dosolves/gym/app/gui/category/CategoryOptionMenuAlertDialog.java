package com.dosolves.gym.app.gui.category;

import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryOptionMenuDialog;
import com.dosolves.gym.domain.category.CategoryShouldBeDeletedCallback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class CategoryOptionMenuAlertDialog implements CategoryOptionMenuDialog{

	private Context context;

	public CategoryOptionMenuAlertDialog(Context context){
		this.context = context;
		
	}
	
	@Override
	public void show(final Category categoryToShowOptionsFor,
					 final CategoryShouldBeDeletedCallback callback) {
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		
		alert.setItems(new String[]{"Delete"},new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callback.onCategoryShouldBeDeleted(categoryToShowOptionsFor);
			}
			
		});
		
		alert.show();
	}

}
