package com.dosolves.gym.app.performance.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.CurrentExerciseHolder;
import com.dosolves.gym.domain.exercise.Exercise;
import com.dosolves.gym.domain.performance.NewSetShouldBeCreatedCallback;

public class PerformanceActivity extends Activity implements CurrentExerciseHolder{

	public static final String EXERCISE_BUNDLE_KEY = "EXERCISE_BUNDLE_KEY";

	private Exercise currentExercise;
	private ListView performanceListView;
	private Button enterButton;
	private EditText repsInput;
	private EditText weightInput;

	private NewSetShouldBeCreatedCallback newSetShouldBeCreatedCallback;

	private PerformanceAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCurrentCategory();		
		setContentView(R.layout.activity_exercise_input);
		setupViewFields();
		setupAdapter();
		setupClickListener();
		setupButtonEnabledListener();
		setupActionBar();
	}

	private void setupAdapter() {
		//performanceListView.setAdapter(adapter);		
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
		return weightInput.getText().length() > 0 && isDouble(weightInput.getText().toString());
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
		return repsInput.getText().length() > 0 && isInt(repsInput.getText().toString());
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
				newSetShouldBeCreatedCallback.onNewSetShouldBeCreated(getReps(), getWeight());
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
		//throwIfNull(performanceListView, enterButton, repsInput, weightInput);
	}
	
	private void throwIfNull(Object... o) {
		int iteration = 0;
		for(Object current:o){
			iteration++;
			if(current == null)
				throw new IllegalStateException(Integer.toString(iteration));
		}
	}

	private void setCurrentCategory() {
		currentExercise = (Exercise)getIntent().getSerializableExtra(EXERCISE_BUNDLE_KEY);
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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

}
