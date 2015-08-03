package com.example.flashalert.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.flashalert.R;
import com.example.flashalert.adapter.SettingViewPagerAdapter;
import com.example.flashalert.controller.MainController;
import com.example.flashalert.view.SlidingTabLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingActivity extends ActionBarActivity {

	private MainController mController;
	private Toolbar mToolbar;
	private ViewPager pager;
	private SettingViewPagerAdapter adapter;
	private SlidingTabLayout tabs;
	private CharSequence Titles[] = { "MAIN", "CUSTOM" };
	int Numboftabs = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		// Creating The Toolbar and setting it as the Toolbar for the activity
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new SettingViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        
        tabs.setSelectedIndicatorColors(R.attr.colorPrimaryDark);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
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
