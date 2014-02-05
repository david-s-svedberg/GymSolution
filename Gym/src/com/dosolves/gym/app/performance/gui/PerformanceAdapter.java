package com.dosolves.gym.app.performance.gui;

import java.util.List;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class PerformanceAdapter extends BaseAdapter implements SetClickedCallback{
	
	private Context context;
	private List<Performance> performances;
	private SetClickedCallback callback;

	public PerformanceAdapter(Context context){
		this.context = context;		
	}

	@Override
	public int getCount() {
		return performances.size();
	}

	@Override
	public Object getItem(int position) {
		return performances.get(position);
	}

	@Override
	public long getItemId(int position) {
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.performance_row_layout, parent, false);
		LinearLayout setsContainer = (LinearLayout)rowView.findViewById(R.id.setsContainer);
		for(Set current: performances.get(position).getSets()){
			setsContainer.addView(createSetButton(current));
		}
		
		return null;
	}

	private View createSetButton(Set set) {
		SetButton newButton = new SetButton(context, set);
		newButton.setSetClickedCallback(this);
		return newButton;
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}
	
	public void setSetClickedCallback(SetClickedCallback callback){
		this.callback = callback;		
	}

	@Override
	public void onSetClicked(Set set) {
		this.callback.onSetClicked(set);		
	}

	public void clear() {
		this.performances = null;
	}

}