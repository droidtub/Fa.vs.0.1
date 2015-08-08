package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.R.id;
import com.example.flashalert.R.layout;
import com.example.flashalert.activity.SettingActivity;
import com.example.flashalert.controller.MainController;
import com.example.flashalert.utils.Properties;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainView implements View.OnClickListener{
	private MainController mController;
	private ActionBarActivity mActivity;
	protected Context mContext;
	private ViewGroup mBottomContainer;
	private View mBottomBarUi;
	private SharedPreferences pref;

	
	public MainView(MainController controller, ActionBarActivity activity){
		mController = controller;
		mActivity = activity;
		pref = mActivity.getApplicationContext().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
	}
	
	
	
	public void initializeUi(){
		mActivity.setContentView(R.layout.activity_main);
		mBottomContainer = (ViewGroup)mActivity.findViewById(R.id.bottom_container);
		ViewStub bottomBarStub = new ViewStub(mActivity, R.layout.bottombar);
		mBottomContainer.addView(bottomBarStub);
        bottomBarStub.setInflatedId(R.id.bottom_bar);
        mBottomBarUi = bottomBarStub.inflate();
        
        ImageButton powerBtn = (ImageButton)mActivity.findViewById(R.id.power_btn);
        powerBtn.setSelected(pref.getBoolean(Properties.POWER_VALUE, false));
        powerBtn.setOnClickListener(this);
        
        ImageButton shareBtn = (ImageButton)mActivity.findViewById(R.id.share_btn);
        shareBtn.setOnClickListener(this);
        ImageButton likeBtn = (ImageButton)mActivity.findViewById(R.id.like_btn);
        likeBtn.setOnClickListener(this);
        
        TextView setting = (TextView)mActivity.findViewById(R.id.settings_btn);
        setting.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.power_btn:
			if(!pref.getBoolean(Properties.POWER_VALUE, false)){
				SharedPreferences.Editor edit = pref.edit();
				edit.putBoolean(Properties.POWER_VALUE, true );
				edit.apply();
			}
			else{
				SharedPreferences.Editor edit = pref.edit();
				edit.putBoolean(Properties.POWER_VALUE, false);
				edit.apply();
			}	
			v.setSelected(pref.getBoolean(Properties.POWER_VALUE, false));
			break;
			
		case R.id.settings_btn:
			Intent i = new Intent(mActivity, SettingActivity.class);
			mActivity.startActivity(i);
			break;

		default:
			break;
		}
	}
}
