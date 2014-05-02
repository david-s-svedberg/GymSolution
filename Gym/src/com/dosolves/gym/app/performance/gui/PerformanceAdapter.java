package com.dosolves.gym.app.performance.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.performance.SetContextualMenuHandler;
import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.Set;

public class PerformanceAdapter extends BaseAdapter {
	
	private Context context;
	private List<Performance> performances = new ArrayList<Performance>();
	private SetContextualMenuHandler contextHandler;

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
		Performance currentPerformance = performances.get(position);
		TextView performanceDate = (TextView)rowView.findViewById(R.id.performanceDate);
		Date date = currentPerformance.getDate();
		performanceDate.setText(getDateString(date));
		for(Set current: currentPerformance.getSets()){
			setsContainer.addView(createSetButton(current));
		}		
		
		return rowView;
	}

	private CharSequence getDateString(Date date) {
		return DateFormat.format("d/M - yy", date);
	}

	private View createSetButton(Set set) {
		return new SetButton(context, set, contextHandler);
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}

	public void clear() {
		this.performances = null;
	}

	public Context getContext() {
		return context;
	}

	public void setSetContextualMenuHandler(SetContextualMenuHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}