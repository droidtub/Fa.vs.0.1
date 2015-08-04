package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.adapter.SettingViewPagerAdapter;
import com.example.flashalert.controller.SettingController;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

public class SettingView {

	private SettingController mController;
	private ActionBarActivity mActivity;
	private Toolbar mToolbar;
	private SettingViewPagerAdapter adapter;
	private ViewPager pager;
	private SlidingTabLayout tabs;
	private CharSequence Titles[] = { "MAIN", "CUSTOM" };
	int Numboftabs = 2;
	
	public SettingView(SettingController controller, ActionBarActivity activity){
		mController = controller;
		mActivity = activity;
	}
	
	public void createSettingUi(){
		mActivity.setContentView(R.layout.activity_setting);
		
		// Creating The Toolbar and setting it as the Toolbar for the activity
		mToolbar = (Toolbar)mActivity.findViewById(R.id.toolbar);
		mActivity.setSupportActionBar(mToolbar);
		mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new SettingViewPagerAdapter(mActivity.getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)mActivity.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)mActivity.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return mActivity.getResources().getColor(R.color.white);
            }
        });
        
        tabs.setSelectedIndicatorColors(R.attr.colorPrimaryDark);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
	}
}
