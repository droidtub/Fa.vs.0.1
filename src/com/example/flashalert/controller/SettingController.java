package com.example.flashalert.controller;

import com.example.flashalert.view.SettingView;

import android.app.Activity;
import android.os.Bundle;

public class SettingController {

	private Activity mActivity;
	private SettingView settingView;
	
	public SettingController(Activity activity){
		mActivity = activity;
		settingView = new SettingView(this, mActivity);
	}
	
	public void onCreate(Bundle savedInstanceState){
		settingView.createSettingUi();
	}
}
