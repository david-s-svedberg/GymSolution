package com.dosolves.gym.utils;

import android.content.Context;
import android.util.TypedValue;

public class GraphicsUtils {

	private static Context context;

	public static float convertDpToPx(float dp) {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}
	
	public static void setContext(Context context) {
		GraphicsUtils.context = context;
	}

	public static float getDimension(int dimensionsId) {
		return context.getResources().getDimension(dimensionsId);		
	}

}
