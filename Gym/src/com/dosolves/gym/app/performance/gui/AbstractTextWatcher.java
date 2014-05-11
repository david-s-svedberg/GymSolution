package com.dosolves.gym.app.performance.gui;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class AbstractTextWatcher implements TextWatcher {
	@Override
	public void afterTextChanged(Editable s) {}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}
}
