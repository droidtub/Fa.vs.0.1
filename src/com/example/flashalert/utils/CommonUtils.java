package com.example.flashalert.utils;

import android.os.Build;
import android.os.Build.VERSION_CODES;

public class CommonUtils {
	
	/**
     * Check if the device runs Android 4.4 (KitKat) or higher.
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

}
