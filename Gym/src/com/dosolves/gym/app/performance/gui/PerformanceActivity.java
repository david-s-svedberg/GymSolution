package com.dosolves.gym.app.performance.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.ads.SystemEventListener;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.ReadyToGetDataCallback;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.NewSetShouldBeCreatedCallback;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;

public class PerformanceActivity extends Activity implements CurrentExerciseHolder, FragmentManagerProvider, SetShouldBeEditedCallback, MenuSetter{

	public static final String EXERCISE_BUNDLE_KEY = "EXERCISE_BUNDLE_KEY";

	private Exercise currentExercise;
	private ListView performanceListView;
	private Button enterButton;
	private EditText repsInput;
	private EditText weightInput;

	private NewSetShouldBeCreatedCallback newSetShouldBeCreatedCallback;
	private SetShouldBeEditedCallback setShouldBeEditedCallback;

	private PerformanceAdapter adapter;

	private ReadyToGetDataCallback readyToGetDataCallback;

	private SystemEventListener systemEventListener;

	private boolean shouldDisplayPurchaseAdsRemovalMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		systemEventListener.onUIAboutToBeShown();
		setCurrentExercise();
		setupViewFields();
		disableEnterButton();
		setupAdapter();
		setupClickListener();
		setupButtonEnabledListener();
		setupActionBar();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		systemEventListener.onMenuShouldBeCreated();
		
		if(shouldDisplayPurchaseAdsRemovalMenu)
			getMenuInflater().inflate(R.menu.only_remove_ads, menu);	
		
		return shouldDisplayPurchaseAdsRemovalMenu;
	}
	
	
	
	private void setCurrentExercise() {
		currentExercise = (Exercise)getIntent().getSerializableExtra(EXERCISE_BUNDLE_KEY);
		setTitle(currentExercise.getName());
	}
	
	private void disableEnterButton() {
		enterButton.setEnabled(false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		readyToGetDataCallback.onReadyToGetData();
	}

	private void setupAdapter() {
		performanceListView.setAdapter(adapter);		
	}

	private void setupButtonEnabledListener() {
		TextWatcher textWatcher = new TextWatcher() {		    
		    @Override
		    public void afterTextChanged(Editable arg0) {
		    	enterButton.setEnabled(repsHasValidValue() && weightHasValidValue());
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		};
		repsInput.addTextChangedListener(textWatcher);
		weightInput.addTextChangedListener(textWatcher);
	}

	protected boolean weightHasValidValue() {
		return weightInput.getText().length() > 0 && isDouble(weightInput.getText().toString()) && doubleIsMoreThenZero(weightInput.getText().toString());
	}

	private boolean doubleIsMoreThenZero(String value) {
		return Double.parseDouble(value) > 0.0;
	}

	private static boolean isDouble(String value) {
		boolean ret = true;
		try{
			Double.parseDouble(value);
		}
		catch(NumberFormatException e){
			ret = false;
		}
		return ret;
	}

	protected boolean repsHasValidValue() {
		return repsInput.getText().length() > 0 && isInt(repsInput.getText().toString()) && intIsMoreThenZero(repsInput.getText().toString());
	}

	private boolean intIsMoreThenZero(String value) {
		return Integer.parseInt(value) > 0;
	}

	private static boolean isInt(String value) {
		boolean ret = true;
		try{
			Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			ret = false;
		}
		return ret;
	}

	private void setupClickListener() {
		enterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideSoftKeyboard();
				
				newSetShouldBeCreatedCallback.onNewSetShouldBeCreated(getReps(), getWeight());				
			}

			private void hideSoftKeyboard() {
				InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
		});
	}
		
	private double getWeight() {
		return Double.parseDouble(weightInput.getText().toString());
	}

	private int getReps() {
		return Integer.parseInt(repsInput.getText().toString());
	}

	private void setupViewFields() {
		performanceListView = (ListView)findViewById(R.id.previousWorkoutsListView);
		enterButton = (Button)findViewById(R.id.enterSetButton);
		repsInput = (EditText)findViewById(R.id.repsInput);
		weightInput = (EditText)findViewById(R.id.weightInput);
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			goBackToExerciseActivityWithPreviousState();			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void goBackToExerciseActivityWithPreviousState() {
		Intent intent = NavUtils.getParentActivityIntent(this); 
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		NavUtils.navigateUpTo(this, intent);
	}

	public void setNewSetShouldBeCreatedCallback(NewSetShouldBeCreatedCallback newSetShouldBeCreatedCallback) {
		this.newSetShouldBeCreatedCallback = newSetShouldBeCreatedCallback;		
	}

	@Override
	public Exercise getCurrentExercise() {
		return currentExercise;
	}

	public void setAdapter(PerformanceAdapter adapter) {
		this.adapter = adapter;		
	}
	
	public void setReadyToGetDataCallback(ReadyToGetDataCallback readyToGetDataCallback) {
		this.readyToGetDataCallback = readyToGetDataCallback;
	}

	public void setSetShouldBeEditedCallback(SetShouldBeEditedCallback setShouldBeEditedCallback) {
		this.setShouldBeEditedCallback = setShouldBeEditedCallback;		
	}

	@Override
	public void onSetShouldBeUpdated(Set set, int newReps, double newWeight) {
		setShouldBeEditedCallback.onSetShouldBeUpdated(set, newReps, newWeight);
	}

	public void setSystemEventListener(SystemEventListener systemEventListener) {
		this.systemEventListener = systemEventListener;
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
