package com.henningta.cryptoquips.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null;
	}

	public static String formatCryptoquipTitle(String title) {
		if (!title.toLowerCase().matches("^\\d{6} [a-z]*")) {
			return title;
		}

		int month = Integer.parseInt(title.substring(0, 2));
		String monthStr = null;
		switch (month) {
			case 1:
				monthStr = "January";
				break;
			case 2:
				monthStr = "February";
				break;
			case 3:
				monthStr = "March";
				break;
			case 4:
				monthStr = "April";
				break;
			case 5:
				monthStr = "May";
				break;
			case 6:
				monthStr = "June";
				break;
			case 7:
				monthStr = "July";
				break;
			case 8:
				monthStr = "August";
				break;
			case 9:
				monthStr = "September";
				break;
			case 10:
				monthStr = "October";
				break;
			case 11:
				monthStr = "November";
				break;
			case 12:
				monthStr = "December";
				break;
			default:
				return title;
		}

		String dayStr = title.substring(2, 4).replaceFirst("^0+(?!$)", "");
		String yearStr = "20" + title.substring(4, 6);

		return monthStr + ' ' + dayStr + ", " + yearStr;
	}

}
