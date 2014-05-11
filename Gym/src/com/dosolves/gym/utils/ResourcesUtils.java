package com.dosolves.gym.utils;

import android.content.Context;
import android.util.TypedValue;

public class ResourcesUtils {

	private static Context context;

	public static float convertDpToPx(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}
	
	public static void setContext(Context context) {
		ResourcesUtils.context = context;
	}

	public static float getDimension(int dimensionsId) {
		return context.getResources().getDimension(dimensionsId);		
	}
	
	public static String getString(int stringId) {
		return context.getResources().getString(stringId);		
	}

}
