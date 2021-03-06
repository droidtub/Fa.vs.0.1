package com.example.flashalert.view;


import com.example.flashalert.R;
import com.example.flashalert.utils.CommonUtils;
import com.example.flashalert.utils.Properties;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class FragmentSettingCustom extends Fragment implements View.OnClickListener,
													TimePickerFragment.TimePickerDialogListener{

	private RelativeLayout startTime;
	private RelativeLayout endTime;
	private RelativeLayout battery;
	private RelativeLayout setTime;
	private TextView startTimeValue;
	private TextView endTimeValue;
	private TextView batteryPercent;
	private SwitchCompat switchSetTime;
	private SharedPreferences pref;
	private static SharedPreferences.Editor editor;
	private BatteryDialog mBatteryDialog;
	private int batteryValue;
	
    private static final int START_TIME_PICKER_ID = 1;
	private static final int END_TIME_PICKER_ID = 2;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_setting_custom,container,false);
        pref = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        
        batteryValue = pref.getInt(Properties.PREF_BATTERY_VALUE, 10);
        
        startTime = (RelativeLayout)v.findViewById(R.id.start_time);
        endTime = (RelativeLayout)v.findViewById(R.id.end_time);
        battery = (RelativeLayout)v.findViewById(R.id.rl_limit_battery);
        setTime = (RelativeLayout)v.findViewById(R.id.set_time);
       
        startTimeValue = (TextView)v.findViewById(R.id.start_time_value);
        endTimeValue = (TextView)v.findViewById(R.id.end_time_value);
        batteryPercent = (TextView)v.findViewById(R.id.on_limit_battery);
        switchSetTime = (SwitchCompat)v.findViewById(R.id.switch_set_time);
        switchSetTime.setChecked(pref.getBoolean(Properties.PREF_SET_TIME_VALUE, false));
        CommonUtils.setSwitchChangeColor(switchSetTime);
        switchSetTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CommonUtils.setSwitchChangeColor(switchSetTime);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(Properties.PREF_SET_TIME_VALUE, isChecked);
				editor.commit();
			}
		});
    	
        switchSetTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = pref.getBoolean(Properties.PREF_SET_TIME_VALUE, false);
				if(switchSetTime != null){
					switchSetTime.setChecked(mValue);
				}
				
			}
		});
        
        startTimeValue.setText(new StringBuilder().append(padding_str(pref.getInt(Properties.PREF_START_HOUR_VALUE, 0))).append(":").append(padding_str(pref.getInt(Properties.PREF_START_MINUTE_VALUE, 0))));
        endTimeValue.setText(new StringBuilder().append(padding_str(pref.getInt(Properties.PREF_END_HOUR_VALUE, 0))).append(":").append(padding_str(pref.getInt(Properties.PREF_END_MINUTE_VALUE, 0))));
        batteryPercent.setText(batteryValue + "%");
        
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        battery.setOnClickListener(this);
        setTime.setOnClickListener(this);
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
			endTimeDialog.setTargetFragment(this, 1);
			endTimeDialog.show(getActivity().getSupportFragmentManager(), "End Time");
			break;
		case R.id.rl_limit_battery:
			showBatteryDialog(getFragmentManager());
			break;
		case R.id.set_time:
			boolean value = !pref.getBoolean(Properties.PREF_SET_TIME_VALUE, false);
			switchSetTime.setChecked(value);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean(Properties.PREF_SET_TIME_VALUE, value);
			editor.commit();
		default:
			break;
		}
	}

	private void showBatteryDialog(FragmentManager manager) {
		mBatteryDialog = new BatteryDialog();
		mBatteryDialog.show(manager, null);
		
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
		if(c > 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	
    class BatteryDialog extends DialogFragment implements OnSeekBarChangeListener, View.OnClickListener{
    	
    	private TextView limitBatteryValue;
    	private SeekBar limitBatterySeekbar;
    	private TextView btnOk;
    	private TextView btnCancel;
    	
    	
    	@Override
    	public Dialog onCreateDialog(Bundle bundle){
    		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		
    		View view = inflater.inflate(R.layout.battery_dialog_layout, null);
    	
    		limitBatteryValue = (TextView)view.findViewById(R.id.limit_battery_value);
    		limitBatterySeekbar = (SeekBar)view.findViewById(R.id.seekbar_limit_battery);
    		btnOk = (TextView)view.findViewById(R.id.batteryOK);
    		btnCancel = (TextView)view.findViewById(R.id.batteryCancel);
    		btnOk.setOnClickListener(this);
    		btnCancel.setOnClickListener(this);
    		limitBatteryValue.setText(pref.getInt(Properties.PREF_BATTERY_VALUE, 10) + "%");
    		limitBatterySeekbar.setProgress(pref.getInt(Properties.PREF_BATTERY_VALUE, 25) - 1);
    		limitBatterySeekbar.setOnSeekBarChangeListener(this);
    		builder.setView(view);
    
    		AlertDialog dialog = builder.create();		
			return dialog;	
    	}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			switch(seekBar.getId()){
			case R.id.seekbar_limit_battery:
				batteryValue = progress + 1;
				limitBatteryValue.setText(batteryValue + "%");
				break;
			default:
				break;
			}
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
			
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			
			
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.batteryOK:
				batteryPercent.setText(batteryValue + "%");
				editor.putInt(Properties.PREF_BATTERY_VALUE , batteryValue);
				editor.commit();
				mBatteryDialog.dismiss();
				break;
			case R.id.batteryCancel:
				mBatteryDialog.dismiss();
				break;
			default:
				break;
			
		}
    }
    }
}