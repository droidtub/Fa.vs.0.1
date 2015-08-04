package com.example.flashalert.service;

import java.util.Timer;
import java.util.TimerTask;

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
	private boolean flashIsOn = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind");
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@SuppressWarnings("deprecation")
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
			flickFlash(false);
//			flashLightOff();
		}

		if (curState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
			// TODO flicker led
			flickFlash(true);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "Create service");
		mContext = this;
	}
	
	public void flickFlash(boolean enable) {
//		if (!enable && flashIsOn) {
//			flashLightOff();
//			return;
//		} else if (!enable && !flashIsOn){
//			flashLightOn();
//			flashLightOff();
//			return;
//		}
		while(true) {
			if (!enable) {
				flashLightOff();
				break;
			}
				
			flashLightOn();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flashLightOff();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
	}
	
	public void flashLightOn() {
	    try {
	        if (getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA_FLASH)) {
	        	flashIsOn = true;
            	cam = Camera.open();
	            Parameters p = cam.getParameters();
	            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
	            cam.setParameters(p);
	            cam.startPreview();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        Toast.makeText(getBaseContext(), "Exception flashLightOn()",
	                Toast.LENGTH_SHORT).show();
	    }
	}

	public void flashLightOff() {
	    try {
	        if (getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA_FLASH)) {
	        	flashIsOn = false;
	            cam.stopPreview();
	            cam.release();
	            cam = null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        Toast.makeText(getBaseContext(), "Exception flashLightOff",
	                Toast.LENGTH_SHORT).show();
	    }
	}
}