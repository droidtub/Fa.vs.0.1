package com.example.flashalert.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.v7.widget.SwitchCompat;

public class CommonUtils {
	
	private SharedPreferences prefs;
	private boolean enable = true;
	
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }
    
    public CommonUtils() {
    	enable = true;
    }
    
	public boolean isFlashEnable() {
		return enable;
	}

	public void setFlashEnable(boolean enable) {
		this.enable = enable;
	}
    
    public void flickFlash(int onLength, int offLength) {
    	flickFlash(onLength, offLength, 300);
    }
    
    public synchronized void flickFlash(final int onLength, final int offLength, final int count) {
    	
		new Thread(new Runnable() {

			@Override
			public void run() {
				Camera cam = Camera.open();
				Parameters params = cam.getParameters();
				cam.startPreview();

				for (int i = 0; i < count; i++) {
					if (!enable) {
						break;
					}
					flipFlash(cam, params, false);
					try {
						Thread.sleep(onLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					flipFlash(cam, params, true);
					try {
						Thread.sleep(offLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				enable = true;
				cam.stopPreview();
				cam.release();
			}
		}).start();

	}

	private void flipFlash(Camera cam, Parameters params, boolean flashIsOn) {
		if (flashIsOn) {
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			cam.setParameters(params);
			flashIsOn = false;
		} else {
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			cam.setParameters(params);
			flashIsOn = true;
		}
	}
    
	public boolean checkSetup(Context context, String type) {
		prefs = context.getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
		int indexProfile = am.getRingerMode();

		boolean valuePower = prefs.getBoolean(Properties.POWER_VALUE, true);
		boolean valueIncomingCall = prefs.getBoolean(Properties.PREF_INCOMING_CALL_VALUE, true);
		boolean valueIncomingSMS = prefs.getBoolean(Properties.PREF_INCOMING_TEXT_VALUE, true);
		boolean valueNormal = prefs.getBoolean(Properties.PREF_NORMAL_MODE_VALUE, true);
		boolean valueVibrate = prefs.getBoolean(Properties.PREF_VIBRATE_MODE_VALUE, false);
		boolean valueSilent = prefs.getBoolean(Properties.PREF_SILENT_MODE_VALUE, false);
		boolean valueHourTurnOn = prefs.getBoolean(Properties.PREF_HOUR_VALUE, false);
//		String hourOn = prefs.getString(Properties.PREF_START_HOUR_VALUE, "19:00");
//		String hourOff = prefs.getString(Properties.PREF_END_HOUR_VALUE, "06:00");

		if (!valuePower) {
			return false;
		}
		
		if (!valueIncomingCall && type.equals(Properties.TYPE_CALL)) {
			return false;
		} else if (!valueIncomingSMS && type.equals(Properties.TYPE_SMS)) {
			return false;
		}

		if (indexProfile == AudioManager.RINGER_MODE_NORMAL) {
			if (!valueNormal) {
				return false;
			}
		}
		else if (indexProfile == AudioManager.RINGER_MODE_VIBRATE) {
			if (!valueVibrate) {
				return false;
			}
		}
		else if (indexProfile == AudioManager.RINGER_MODE_SILENT) {
			if (!valueSilent) {
				return false;
			}
		}
		
		//TODO check hour on / off

		return true;
	}
	
	public static void setSwitchChangeColor(SwitchCompat sw) {
		if(sw.isChecked()){
			sw.getThumbDrawable().setColorFilter(Color.parseColor("#f9f6f6"),Mode.MULTIPLY );
			sw.getTrackDrawable().setColorFilter(Color.parseColor("#80f9f6f6"),Mode.MULTIPLY );
    	} else {
    		sw.getThumbDrawable().setColorFilter(Color.parseColor("#8f8e8e"),Mode.MULTIPLY );
    		sw.getTrackDrawable().setColorFilter(Color.parseColor("#608f8e8e"),Mode.MULTIPLY );
    	}
	}

}
