package com.dosolves.gym.app;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextPreferenceRetriever implements PreferenceRetriever{

	private Context context;

	public ContextPreferenceRetriever(Context context) {
		this.context = context;

	}

	@Override
	public SharedPreferences getPreferences() {
		return context.getSharedPreferences("com.dosolves.gym", Context.MODE_PRIVATE);
	}

}
