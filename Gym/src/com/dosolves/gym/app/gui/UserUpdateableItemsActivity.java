package com.dosolves.gym.app.gui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.AdsUserGestureListener;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.domain.ReadyToGetDataCallback;
import com.dosolves.gym.domain.SystemEventObservableImpl;

public abstract class UserUpdateableItemsActivity extends ListActivity implements MenuSetter, PositionToIdTranslator{

	private boolean shouldDisplayPurchaseAdsRemovalMenu;
	
	private AddItemRequestedCallBack addItemRequestedCallBack;
	private OpenItemRequestedCallback openItemRequestedCallback;
	private ReadyToGetDataCallback readyToGetDataCallback;
	private AdsUserGestureListener adsUserGestureListener;
	private MultiChoiceModeListener multiChoiceModeListener;
	
	private SystemEventObservableImpl systemEventListeners = new SystemEventObservableImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		systemEventListeners.notifyUIAboutToBeCreated();
		ListView listView = this.getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(multiChoiceModeListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		readyToGetDataCallback.onReadyToGetData();
		systemEventListeners.notifyUIInteractive();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		systemEventListeners.notifyUIHidden();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		systemEventListeners.notifyUIDestroyed();
	}
	
	public void setAddItemRequestedCallBack(AddItemRequestedCallBack addItemRequestedCallBack) {
		this.addItemRequestedCallBack = addItemRequestedCallBack;		
	}
	
	public void setOpenItemRequestedCallback(OpenItemRequestedCallback openItemRequestedCallback) {
		this.openItemRequestedCallback = openItemRequestedCallback;
	}
	
	public void setReadyToGetDataCallback(ReadyToGetDataCallback readyToGetDataCallback) {
		this.readyToGetDataCallback = readyToGetDataCallback;
	}
	
	public void addSystemEventListener(SystemEventListener systemEventListener) {
		systemEventListeners.registerSystemEventListener(systemEventListener);
	}
	
	public void setAdsUserGestureListener(AdsUserGestureListener adsUserGestureListener) {
		this.adsUserGestureListener = adsUserGestureListener;
	}
	
	public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
		this.multiChoiceModeListener = multiChoiceModeListener;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		systemEventListeners.notifyMenuShouldBeCreated();
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
	
}
