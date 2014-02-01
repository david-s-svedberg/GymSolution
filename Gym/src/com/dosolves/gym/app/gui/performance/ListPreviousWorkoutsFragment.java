package com.dosolves.gym.app.gui.performance;

import com.dosolves.gym.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListPreviousWorkoutsFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_previous_workouts_view, container, false);
	}
	
}
