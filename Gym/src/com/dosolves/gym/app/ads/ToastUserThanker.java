package com.dosolves.gym.app.ads;

import android.content.Context;
import android.widget.Toast;

import com.dosolves.gym.R;
import com.dosolves.gym.ads.UserThanker;

public class ToastUserThanker implements UserThanker {

	private Context context;

	public ToastUserThanker(Context context) {
		this.context = context;
	}

	@Override
	public void thankUser() {
		Toast.makeText(context, R.string.thank_you, Toast.LENGTH_LONG).show();
	}

}
