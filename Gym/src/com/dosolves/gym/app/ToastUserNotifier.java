package com.dosolves.gym.app;

import android.content.Context;
import android.widget.Toast;

import com.dosolves.gym.R;
import com.dosolves.gym.domain.UserNotifier;

public class ToastUserNotifier implements UserNotifier {
		
	private Context context;

	public ToastUserNotifier(Context context) {
		this.context = context;		
	}

	@Override
	public void notifyThatDefaultExercisesHaveBeenCreated() {
		Toast.makeText(this.context, R.string.default_exercises_created, Toast.LENGTH_LONG).show();
	}

}
