package com.example.flashalert.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.flashalert.R;
import com.example.flashalert.adapter.SettingViewPagerAdapter;
import com.example.flashalert.controller.MainController;
import com.example.flashalert.controller.SettingController;
import com.example.flashalert.view.SlidingTabLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingActivity extends ActionBarActivity {

	private SettingController settingController;
	private Toolbar mToolbar;
	private ViewPager pager;
	private SettingViewPagerAdapter adapter;
	private SlidingTabLayout tabs;
	private CharSequence Titles[] = { "MAIN", "CUSTOM" };
	int Numboftabs = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settingController = new SettingController(this);
		settingController.onCreate(savedInstanceState);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
