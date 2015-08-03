package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.R.id;
import com.example.flashalert.R.layout;
import com.example.flashalert.adapter.MainViewAdapter;
import com.example.flashalert.controller.MainController;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MainView {
	private MainController mController;
	private ActionBarActivity mActivity;
	protected Context mContext;
	protected FrameLayout mMainLayout;
	private Toolbar mToolbar;
	private RecyclerView recyclerView;
	private MainViewAdapter mAdapter;
	
	public MainView(MainController controller, ActionBarActivity activity){
		mController = controller;
		mActivity = activity;
	}
	
	/*public void initializeUi(Context context, View view ){
		mContext = context;
		mMainLayout = (FrameLayout)view.findViewById(android.R.id.content);
		LayoutInflater.from(mContext).inflate(R.layout.activity_main, mMainLayout);
		
		
	}*/
	
	public void initializeUi(){
		mActivity.setContentView(R.layout.fragment_setting_normal);
		mToolbar = (Toolbar)mActivity.findViewById(R.id.toolbar);
		mActivity.setSupportActionBar(mToolbar);
		mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
		
//		recyclerView = (RecyclerView)mActivity.findViewById(R.id.container_body);
//		recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//		recyclerView.setHasFixedSize(true);
//		mAdapter = new MainViewAdapter(this);
//		recyclerView.setAdapter(mAdapter);
	}
}
