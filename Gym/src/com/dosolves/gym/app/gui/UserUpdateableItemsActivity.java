package com.dosolves.gym.app.gui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.AdsUserGestureListener;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.domain.ItemMenuRequestedCallback;
import com.dosolves.gym.domain.ReadyToGetDataCallback;

public abstract class UserUpdateableItemsActivity extends ListActivity implements OnItemLongClickListener, MenuSetter{

	private boolean shouldDisplayPurchaseAdsRemovalMenu;
	
	private AddItemRequestedCallBack addItemRequestedCallBack;
	private ItemMenuRequestedCallback itemMenuRequestedCallback;
	private OpenItemRequestedCallback openItemRequestedCallback;
	private ReadyToGetDataCallback readyToGetDataCallback;
	private SystemEventListener systemEventListener;
	private AdsUserGestureListener adsUserGestureListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		systemEventListener.onUIAboutToBeCreated();
		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		readyToGetDataCallback.onReadyToGetData();
	}
	
	public void setAddItemRequestedCallBack(AddItemRequestedCallBack addItemRequestedCallBack) {
		this.addItemRequestedCallBack = addItemRequestedCallBack;		
	}
	
	public void setItemMenuRequestedCallback(ItemMenuRequestedCallback itemMenuRequestedCallback) {
		this.itemMenuRequestedCallback = itemMenuRequestedCallback;
	}

	public void setOpenItemRequestedCallback(OpenItemRequestedCallback openItemRequestedCallback) {
		this.openItemRequestedCallback = openItemRequestedCallback;
	}
	
	public void setReadyToGetDataCallback(ReadyToGetDataCallback readyToGetDataCallback) {
		this.readyToGetDataCallback = readyToGetDataCallback;
	}
	
	public void setSystemEventListener(SystemEventListener systemEventListener) {
		this.systemEventListener = systemEventListener;
	}
	
	public void setAdsUserGestureListener(AdsUserGestureListener adsUserGestureListener) {
		this.adsUserGestureListener = adsUserGestureListener;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		systemEventListener.onMenuShouldBeCreated();
		getMenuInflater().inflate(decideMenuIdToShow(), menu);
		return true;
	}

	private int decideMenuIdToShow() {
		return shouldDisplayPurchaseAdsRemovalMenu ? R.menu.items_with_remove_ads : R.menu.items;
	}
	
	@Override	
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = false;
		
		switch (item.getItemId()){
			case R.id.add_item:
				addItemRequestedCallBack.onAddItemRequested();
				ret = true;
				break;
			case R.id.purchase_remove_ads:
				adsUserGestureListener.onPurchaseAdsRemovalRequested();
				ret = true;
				break;
			default:
				ret = super.onOptionsItemSelected(item);					
		}
		
		return ret;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int postition, long id) {
		itemMenuRequestedCallback.onItemMenuRequested(postition);
		return true;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		openItemRequestedCallback.onOpenItemRequested(position);
	}

	@Override
	public void setAdsMenu() {
		shouldDisplayPurchaseAdsRemovalMenu = true;
	}

	@Override
	public void setAdsFreeMenu() {
		shouldDisplayPurchaseAdsRemovalMenu = false;
	}
	
	protected abstract int getIdOfItemAtPosition(int postition);
	
}
