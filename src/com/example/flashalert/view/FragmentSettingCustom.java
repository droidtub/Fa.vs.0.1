package com.example.flashalert.view;


import com.example.flashalert.R;
import com.example.flashalert.utils.CommonUtils;
import com.example.flashalert.utils.Properties;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class FragmentSettingCustom extends Fragment implements View.OnClickListener,
													TimePickerFragment.TimePickerDialogListener{

	private SwitchCompat notifiApps;
	private RelativeLayout startTime;
	private RelativeLayout endTime;
	private TextView startTimeValue;
	private TextView endTimeValue;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
    private static final int START_TIME_PICKER_ID = 1;
	private static final int END_TIME_PICKER_ID = 2;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_setting_custom,container,false);
        pref = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        notifiApps = (SwitchCompat)v.findViewById(R.id.switch_notification_apps);
        notifiApps.setChecked(pref.getBoolean(Properties.PREF_INCOMING_TEXT_VALUE, true));
        CommonUtils.setSwitchChangeColor(notifiApps);
        notifiApps.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(Properties.PREF_INCOMING_TEXT_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(notifiApps);
			}
		});
    	
        notifiApps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = pref.getBoolean(Properties.PREF_INCOMING_TEXT_VALUE, false);
				if(notifiApps != null){
					notifiApps.setChecked(mValue);
				}
				
			}
		});
        
        startTime = (RelativeLayout)v.findViewById(R.id.start_time);
        endTime = (RelativeLayout)v.findViewById(R.id.end_time);
        startTimeValue = (TextView)v.findViewById(R.id.start_time_value);
        endTimeValue = (TextView)v.findViewById(R.id.end_time_value);
        startTimeValue.setText(new StringBuilder().append(padding_str(pref.getInt(Properties.PREF_START_HOUR_VALUE, 0))).append(":").append(padding_str(pref.getInt(Properties.PREF_START_MINUTE_VALUE, 0))));
        endTimeValue.setText(new StringBuilder().append(padding_str(pref.getInt(Properties.PREF_END_HOUR_VALUE, 0))).append(":").append(padding_str(pref.getInt(Properties.PREF_END_MINUTE_VALUE, 0))));
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        return v;
    }

	@Override
	public void onClick(View v) {	
		switch (v.getId()) {
		case R.id.start_time:
			DialogFragment startTimeDialog = TimePickerFragment.newInstance(START_TIME_PICKER_ID);
			startTimeDialog.setTargetFragment(this, 1);
			startTimeDialog.show(getActivity().getSupportFragmentManager(), "Start Time");			
			break;
		case R.id.end_time:
			DialogFragment endTimeDialog = TimePickerFragment.newInstance(END_TIME_PICKER_ID);
			endTimeDialog.show(getActivity().getSupportFragmentManager(), "End Time");
		default:
			break;
		}
	}


	@Override
	public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute) {
		if(id == START_TIME_PICKER_ID){
			startTimeValue.setText(new StringBuilder().append(padding_str(hourOfDay)).append(":").append(padding_str(minute)));
			editor.putInt(Properties.PREF_START_HOUR_VALUE, hourOfDay);
			editor.putInt(Properties.PREF_START_MINUTE_VALUE, minute);
			editor.commit();
		}
		else if(id == END_TIME_PICKER_ID)
			endTimeValue.setText(new StringBuilder().append(padding_str(hourOfDay)).append(":").append(padding_str(minute)));
			editor.putInt(Properties.PREF_END_HOUR_VALUE, hourOfDay);
			editor.putInt(Properties.PREF_END_MINUTE_VALUE, minute);
			editor.commit();
	}
    
	
	private static String padding_str(int c){
		if(c > 0)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	
    
}
