package com.example.flashalert.controller;

import com.example.flashalert.view.SettingView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SettingController {

	private ActionBarActivity mActivity;
	private SettingView settingView;
	
	public SettingController(ActionBarActivity activity){
		mActivity = activity;
		settingView = new SettingView(this, mActivity);
	}
	
	public void onCreate(Bundle savedInstanceState){
		settingView.createSettingUi();
	}
}
