package com.dosolves.gym;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class CategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}
	
	@Override	
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = false;
		
		switch (item.getItemId()){
			case R.id.add_category:
				ret = true;
				break;
			default:
				ret = super.onOptionsItemSelected(item);					
		}
		
		return ret;
	}

}
