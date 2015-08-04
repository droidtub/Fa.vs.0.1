package com.example.flashalert.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

    Context context = null;
    private static final String TAG = PhoneReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        }
        
        // TODO check sharepreferences (normal, vibrate, silent) -> return / ok
        
        context.startService(new Intent(context,
				com.example.flashalert.service.CallService.class)
				.setAction(intent.getAction())
				.putExtra(TelephonyManager.EXTRA_INCOMING_NUMBER,intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER))
				.putExtra(TelephonyManager.EXTRA_STATE,intent.getStringExtra(TelephonyManager.EXTRA_STATE)));
    }
}
