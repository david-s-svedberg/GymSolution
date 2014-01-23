package com.dosolves.gym.app.gui.category;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.ItemMenuRequestedCallback;
import com.dosolves.gym.domain.category.AddCategoryRequestedCallBack;

public class CategoryActivity extends ListActivity implements OnItemLongClickListener {

	private AddCategoryRequestedCallBack addCategoryRequestedCallBack;
	private ItemMenuRequestedCallback itemMenuRequestedCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(this);
		
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
				addCategoryRequestedCallBack.onAddCategoryRequested();
				ret = true;
				break;
			default:
				ret = super.onOptionsItemSelected(item);					
		}
		
		return ret;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	public void setAddCategoryRequestedCallBack(AddCategoryRequestedCallBack callback) {
		this.addCategoryRequestedCallBack = callback;		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int postition, long id) {
		itemMenuRequestedCallback.onItemMenuRequested(postition);
		return true;
	}

	public void setItemMenuRequestedCallback(ItemMenuRequestedCallback callback) {
		this.itemMenuRequestedCallback = callback;

	}

}
