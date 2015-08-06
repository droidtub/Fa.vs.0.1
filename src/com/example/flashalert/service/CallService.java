package com.example.flashalert.service;

import java.util.Timer;
import java.util.TimerTask;

import com.example.flashalert.utils.Properties;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallService extends Service {

	public static final String TAG = CallService.class.getName();
	private String curState = TelephonyManager.EXTRA_STATE_IDLE;
	private Context mContext;
	private SharedPreferences prefs;
	private Camera cam;
	private Parameters params;
	private boolean flashIsOn = false;
	private boolean enable = true;
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind");
		throw new UnsupportedOperationException("Not yet implemented");
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "Create service");
		mContext = this;
		prefs = mContext.getApplicationContext().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		if (intent == null 
				|| intent.getAction() == null 
				|| !intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			
			return super.onStartCommand(intent, flags, startId);
			
		}
		curState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		if (curState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)
				|| curState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

			enable = false;
		}

		if (curState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
	
			// TODO flicker led
			enable = true;
			int onLength = prefs.getInt(Properties.INCOMING_CALL_ON_LENGTH, Properties.VALUE_DEFAULT_FLASH_ON_OFF);
			int offLength = prefs.getInt(Properties.INCOMING_CALL_OFF_LENGTH, Properties.VALUE_DEFAULT_FLASH_ON_OFF);
			flickFlash(onLength, offLength);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void flickFlash(final int onLength, final int offLength) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				cam = Camera.open();
			    params = cam.getParameters();
			    cam.startPreview();

			    while (enable) {
			        flipFlash();
			        try {
						Thread.sleep(onLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			        flipFlash();
			        try {
						Thread.sleep(offLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    }
			    cam.stopPreview();
			    cam.release();
			}
		}).start();
		
	}

	private void flipFlash(){
	    if (flashIsOn) {
	        params.setFlashMode(Parameters.FLASH_MODE_OFF);
	        cam.setParameters(params);
	        flashIsOn = false;
	    } else{
	        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
	        cam.setParameters(params);
	        flashIsOn = true;
	    }
	}
	

}