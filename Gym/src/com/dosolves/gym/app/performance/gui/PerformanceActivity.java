package com.dosolves.gym.app.performance.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.AdsUserGestureListener;
import com.dosolves.gym.ads.MenuSetter;
import com.dosolves.gym.app.SystemEventListener;
import com.dosolves.gym.app.gui.ActionModeStarter;
import com.dosolves.gym.app.gui.FragmentManagerProvider;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.SystemEventObservable;
import com.dosolves.gym.domain.SystemEventObservableImpl;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.domain.performance.SetShouldBeEditedCallback;
import com.dosolves.gym.domain.performance.StartValueSetter;
import com.dosolves.gym.utils.StringUtils;

public class PerformanceActivity extends Activity implements CurrentExerciseHolder, 
															 FragmentManagerProvider, 
															 SetShouldBeEditedCallback, 
															 MenuSetter, 
															 StartValueSetter, 
															 ActionModeStarter {

	public static final String EXERCISE_BUNDLE_KEY = "EXERCISE_BUNDLE_KEY";

	private boolean shouldDisplayPurchaseAdsRemovalMenu;
	
	private Exercise currentExercise;
	private ListView performanceListView;
	private Button enterButton;
	private Button repsPlusButton;
	private Button repsMinusButton;
	private Button weightPlusButton;
	private Button weightMinusButton;
	private EditText repsInput;
	private EditText weightInput;

	private UserGestureObservable userGestureListeners = new UserGestureObservableImpl(); 
	private SystemEventObservable systemEventListeners = new SystemEventObservableImpl();
	private AdsUserGestureListener adsUserGestureListener;
	
	private PerformanceAdapter adapter;		
	private SetTextHandler setTextHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		systemEventListeners.notifyUIAboutToBeCreated();
		setCurrentExercise();
		setupViewFields();
		disableEnterButton();
		setupAdapter();
		setupClickListeners();
		setupButtonEnabledListener();
		setupActionBar();
		systemEventListeners.notifyUICreated();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		systemEventListeners.notifyMenuShouldBeCreated();
		
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

	private void setupAdapter() {
		performanceListView.setAdapter(adapter);		
	}

	private void setupButtonEnabledListener() {
		setTextHandler = new SetTextHandlerImpl(enterButton, repsInput, weightInput);
		repsInput.addTextChangedListener(setTextHandler);
		weightInput.addTextChangedListener(setTextHandler);
	}

	private void setupClickListeners() {
		enterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideSoftKeyboard();
				
				userGestureListeners.notifyNewSetShouldBeCreated(setTextHandler.getReps(), setTextHandler.getWeight());				
			}

			private void hideSoftKeyboard() {
				InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
		});
		
		repsPlusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setTextHandler.incrementRepsText();
			}
			
		});
		
		repsMinusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setTextHandler.decrementRepsText();
			}
			
		});
		
		weightPlusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setTextHandler.incrementWeightText();
			}
			
		});
		
		weightMinusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setTextHandler.decrementWeightText();
			}
			
		});
		
	}
	
	private void setupViewFields() {
		performanceListView = (ListView)findViewById(R.id.previousWorkoutsListView);
		enterButton = (Button)findViewById(R.id.enterSetButton);
		repsInput = (EditText)findViewById(R.id.repsInput);
		weightInput = (EditText)findViewById(R.id.weightInput);
		repsPlusButton = (Button)findViewById(R.id.reps_plus_button);
		repsMinusButton = (Button)findViewById(R.id.reps_minus_button);
		weightPlusButton = (Button)findViewById(R.id.weight_plus_button);
		weightMinusButton = (Button)findViewById(R.id.weight_minus_button);
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = false;
		
		switch (item.getItemId()) {
		case android.R.id.home:
			goBackToExerciseActivityWithPreviousState();			
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

	private void goBackToExerciseActivityWithPreviousState() {
		Intent intent = NavUtils.getParentActivityIntent(this); 
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		NavUtils.navigateUpTo(this, intent);
	}

	public void addUserGestureListener(UserGestureListener listener) {
		this.userGestureListeners.registerUserGestureListener(listener);		
	}
	
	public void setAdapter(PerformanceAdapter adapter) {
		this.adapter = adapter;		
	}
	
	public void setAdsUserGestureListener(AdsUserGestureListener adsUserGestureListener) {
		this.adsUserGestureListener = adsUserGestureListener;
	}

	@Override
	public Exercise getCurrentExercise() {
		return currentExercise;
	}

	@Override
	public void onSetShouldBeUpdated(Set set, int newReps, double newWeight) {
		userGestureListeners.notifySetShouldBeUpdated(set, newReps, newWeight);
	}
	
	@Override
	public void setAdsMenu() {
		shouldDisplayPurchaseAdsRemovalMenu = true;
	}

	@Override
	public void setAdsFreeMenu() {
		shouldDisplayPurchaseAdsRemovalMenu = false;
	}

	@Override
	public void setStartValues(Set set) {
		repsInput.setText(String.format("%d",set.getReps()));
		weightInput.setText(StringUtils.doubleToStringRemoveTrailingZeros(set.getWeight()));
	}

	public void addSystemEventListener(SystemEventListener systemEventListener) {
		systemEventListeners.registerSystemEventListener(systemEventListener);
	}
		
}
