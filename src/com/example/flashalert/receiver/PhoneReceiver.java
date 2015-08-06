package com.example.flashalert.receiver;

import com.example.flashalert.utils.Properties;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

	private static final String TAG = PhoneReceiver.class.getName();
	private SharedPreferences prefs;
	Context context = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			return;
		}
		// TODO check sharepreferences (normal, vibrate, silent) -> return / ok
		if (!checkSetup(context.getApplicationContext())) {
			return;
		}

		context.startService(
				new Intent(context, com.example.flashalert.service.CallService.class).setAction(intent.getAction())
						.putExtra(TelephonyManager.EXTRA_INCOMING_NUMBER,
								intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER))
				.putExtra(TelephonyManager.EXTRA_STATE, intent.getStringExtra(TelephonyManager.EXTRA_STATE)));
	}

	public boolean checkSetup(Context context) {
		prefs = context.getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
		int indexProfile = am.getRingerMode();

		int valueIncomingCall = prefs.getInt(Properties.INCOMING_CALL, Properties.VALUE_INCOMING_CALL_ON);
		int valueNormal = prefs.getInt(Properties.NORMAL, Properties.VALUE_NORMAL_ON);
		int valueVibrate = prefs.getInt(Properties.VIBRATE, Properties.VALUE_VIBRATE_OFF);
		int valueSilent = prefs.getInt(Properties.SILENT, Properties.VALUE_SILENT_OFF);
		int valueBatteryPercent = prefs.getInt(Properties.BATTERY_PERCENT, Properties.VALUE_DEFAULT_BATTERY_PERCENT);
		int valueTurnOnHour = prefs.getInt(Properties.TURN_ON_HOUR, Properties.VALUE_TURN_ON_HOUR_OFF);
		String hourOn = prefs.getString(Properties.HOUR_ON, "19:00");
		String hourOff = prefs.getString(Properties.HOUR_OFF, "06:00");

		if (valueIncomingCall == Properties.VALUE_INCOMING_CALL_OFF) {
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
}
