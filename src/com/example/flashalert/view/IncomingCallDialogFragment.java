package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.utils.CommonUtils;
import com.example.flashalert.utils.Properties;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class IncomingCallDialogFragment extends DialogFragment implements OnSeekBarChangeListener, View.OnClickListener{

	private int onProgress = 500;
	private int offProgress = 500;
	
	private SharedPreferences pref;
	private SharedPreferences.Editor edit;
	private TextView onLength;
	private TextView offLength;
	private TextView btnOk;
	private TextView btnCancel;
	private TextView btnTest;
	private CommonUtils utils;
	private boolean isEnable = true;
	
	@Override
    public Dialog onCreateDialog(Bundle bundle){
		pref = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		edit = pref.edit();
		utils = new CommonUtils();
		AlertDialog.Builder callBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater callInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		View view = callInflater.inflate(R.layout.call_dialog_layout, null);
		SeekBar mSeekBarOnLength = (SeekBar) view.findViewById(R.id.seekbar_on_length);
		mSeekBarOnLength.setOnSeekBarChangeListener(this);
		SeekBar mSeekBarOffLength = (SeekBar)view.findViewById(R.id.seekbar_off_length);
		mSeekBarOffLength.setOnSeekBarChangeListener(this);
		
		btnCancel = (TextView)view.findViewById(R.id.callCancel);
		btnCancel.setOnClickListener(this);
		btnOk = (TextView)view.findViewById(R.id.callOk);
		btnOk.setOnClickListener(this);
		btnTest = (TextView)view.findViewById(R.id.callTest);
		btnTest.setOnClickListener(this);
		
		onLength = (TextView)view.findViewById(R.id.on_length_ms);
	    offLength = (TextView)view.findViewById(R.id.off_length_ms);
		mSeekBarOnLength.setProgress(pref.getInt(Properties.PREF_CALL_ON_LENGTH_VALUE, 500) - 500);
		mSeekBarOffLength.setProgress(pref.getInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, 500) - 500);
		onLength.setText(pref.getInt(Properties.PREF_CALL_ON_LENGTH_VALUE, 500) + " ms");
		offLength.setText(pref.getInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, 500) + " ms");
		callBuilder.setView(view);
				/*.setTitle(R.string.call_title)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edit.putInt(Properties.PREF_CALL_ON_LENGTH_VALUE, onProgress);
						edit.putInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, offProgress);
						edit.commit();
						
					}
				})
				.setNeutralButton(R.string.test_on, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
					}
				});*/
		
		AlertDialog dialog = callBuilder.create();
		return dialog;
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.seekbar_on_length:
			onProgress = progress + 500;
			onLength.setText(onProgress + " ms");
			break;
		case R.id.seekbar_off_length:
			offProgress = progress + 500;
			offLength.setText(offProgress + " ms");
		default:
			break;
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.callOk:
			edit.putInt(Properties.PREF_CALL_ON_LENGTH_VALUE, onProgress);
			edit.putInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, offProgress);
			edit.commit();
			this.dismiss();
			break;

		case R.id.callCancel:
			this.dismiss();
			break;
		case R.id.callTest:
			if (isEnable) {
				utils.flickFlash(500, 500);
				isEnable = false;
			} else {
				utils.setFlashEnable(false);
				isEnable = true;
			}
			break;
		default:
			break;
		}
		
	}
    
}