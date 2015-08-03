package com.example.flashalert.adapter;

import com.example.flashalert.view.MainView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ViewHolder> {

	private MainView mv;
	
	public MainViewAdapter(MainView mv){
		this.mv = mv;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{

		public ViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
		}
		
	}
}
