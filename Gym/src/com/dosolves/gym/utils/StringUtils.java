package com.dosolves.gym.utils;

import android.annotation.SuppressLint;

public class StringUtils {

	public static String reverse(String original) {
		return new StringBuilder(original).reverse().toString();
	}
	
	@SuppressLint("DefaultLocale")
	public static String doubleToStringRemoveTrailingZeros(double d)
	{
	    if(d == (int) d)
	        return String.format("%d",(int)d);
	    else
	        return String.format("%s",d);
	}

}
