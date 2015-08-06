package com.example.flashalert.receiver;

import com.example.flashalert.utils.Properties;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.provider.Telephony.Sms.Intents;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class MessagingReceiver extends WakefulBroadcastReceiver {

	private static final String TAG = MessagingReceiver.class.getName();
	private SharedPreferences prefs;
	private Context context = null;
	private Camera cam;
	private Parameters params;
	
    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.e(TAG, "OnReceive");
    	if (intent == null || intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			return;
		}

    	if (!checkSetup(context.getApplicationContext())) {
			return;
		}
    	
    	int onLength = prefs.getInt(Properties.INCOMING_SMS_ON_LENGTH, Properties.VALUE_DEFAULT_FLASH_ON_OFF);
		int offLength = prefs.getInt(Properties.INCOMING_SMS_OFF_LENGTH, Properties.VALUE_DEFAULT_FLASH_ON_OFF);
		int countBlink = prefs.getInt(Properties.INCOMING_SMS_BLINK_COUNT, Properties.VALUE_DEFAULT_SMS_BLINK_COUNT);
		flickFlash(onLength, offLength, countBlink);
		
		completeWakefulIntent(intent);
    	
    }
    
    public boolean checkSetup(Context context) {
		prefs = context.getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
		int indexProfile = am.getRingerMode();

		int valueIncomingSMS = prefs.getInt(Properties.INCOMING_SMS, Properties.VALUE_INCOMING_SMS_ON);
		int valueNormal = prefs.getInt(Properties.NORMAL, Properties.VALUE_NORMAL_ON);
		int valueVibrate = prefs.getInt(Properties.VIBRATE, Properties.VALUE_VIBRATE_OFF);
		int valueSilent = prefs.getInt(Properties.SILENT, Properties.VALUE_SILENT_OFF);
		int valueBatteryPercent = prefs.getInt(Properties.BATTERY_PERCENT, Properties.VALUE_DEFAULT_BATTERY_PERCENT);
		int valueTurnOnHour = prefs.getInt(Properties.TURN_ON_HOUR, Properties.VALUE_TURN_ON_HOUR_OFF);
		String hourOn = prefs.getString(Properties.HOUR_ON, "19:00");
		String hourOff = prefs.getString(Properties.HOUR_OFF, "06:00");

		if (valueIncomingSMS == Properties.VALUE_INCOMING_SMS_OFF) {
			return false;
		}

		if (indexProfile == AudioManager.RINGER_MODE_NORMAL) {
			if (valueNormal == Properties.VALUE_NORMAL_OFF) {
				return false;
			}
		}
		else if (indexProfile == AudioManager.RINGER_MODE_VIBRATE) {
			if (valueVibrate == Properties.VALUE_VIBRATE_OFF) {
				return false;
			}
		}
		else if (indexProfile == AudioManager.RINGER_MODE_SILENT) {
			if (valueSilent == Properties.VALUE_SILENT_OFF) {
				return false;
			}
		}
		
		//TODO check battery percent and hour on / off

		return true;
	}
    
    public void flickFlash(int onLength, int offLength, int countBlink) {

    	cam = Camera.open();
    	params = cam.getParameters();
	    cam.startPreview();

	    for (int i = 0; i < countBlink; i++) {
	        flipFlash(false);
	        try {
				Thread.sleep(onLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        flipFlash(true);
	        try {
				Thread.sleep(offLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    cam.stopPreview();
	    cam.release();
	}

	private void flipFlash(boolean flashIsOn){
	    if (flashIsOn) {
	        params.setFlashMode(Parameters.FLASH_MODE_OFF);
	        cam.setParameters(params);
	    } else{
	        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
	        cam.setParameters(params);
	    }
	}
}
