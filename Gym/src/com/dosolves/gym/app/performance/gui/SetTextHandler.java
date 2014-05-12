package com.dosolves.gym.app.performance.gui;

import android.text.TextWatcher;

public interface SetTextHandler extends TextWatcher {

	public double getWeight();

	public int getReps();

	public void decrementRepsText();

	public void incrementRepsText();

}