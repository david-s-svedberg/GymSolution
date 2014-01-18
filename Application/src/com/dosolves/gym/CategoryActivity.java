package com.dosolves.gym;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CategoryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(createAdapter());
	}

	private ListAdapter createAdapter() {
		DbStructureGiver categoryStructure = new CategoryDbStructureGiver();
		DataAccess dao = new SQLiteDataAccess(SQLiteOpenHelperSingeltonHolder.getDbHelper(), categoryStructure);
		CursorCategoryFactory categoryFactory = new CursorCategoryFactory(categoryStructure);
		CategoryRetriever retriever = new CursorCategoryRetriever(dao, categoryFactory);
		return new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, retriever.getCategories());		
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

}
