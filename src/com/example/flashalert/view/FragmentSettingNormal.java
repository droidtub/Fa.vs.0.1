package com.example.flashalert.view;


import com.example.flashalert.R;
import com.example.flashalert.utils.CommonUtils;
import com.example.flashalert.utils.Properties;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


public class FragmentSettingNormal extends Fragment implements View.OnClickListener{
	private RelativeLayout incomingCall;
	private RelativeLayout incomingText;
	private RelativeLayout normalMode;
	private RelativeLayout vibrateMode;
	private RelativeLayout silentMode;
	private SharedPreferences preferences;
	private SwitchCompat incomingCallCheckbox;
	private SwitchCompat incomingTextCheckbox;
	private SwitchCompat normalCheckbox;
	private SwitchCompat vibrateCheckbox;
	private SwitchCompat silentCheckbox;
	
	private IncomingCallDialogFragment mCallDialog;
	private IncomingTextDialogFragment mTextDialog;

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	preferences = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
    	View v = inflater.inflate(R.layout.fragment_setting_normal,container,false);

        incomingCall = (RelativeLayout)v.findViewById(R.id.rl_incoming_call);
        incomingText = (RelativeLayout)v.findViewById(R.id.rl_incoming_sms);
        normalMode = (RelativeLayout)v.findViewById(R.id.rl_normal_mode);
        vibrateMode = (RelativeLayout)v.findViewById(R.id.rl_vibrate_mode);
        silentMode = (RelativeLayout)v.findViewById(R.id.rl_silent_mode);
        
        incomingCallCheckbox = (SwitchCompat)v.findViewById(R.id.switch_incoming_call);
        incomingCallCheckbox.setChecked(preferences.getBoolean(Properties.PREF_INCOMING_CALL_VALUE, true));
        CommonUtils.setSwitchChangeColor(incomingCallCheckbox);
        
        incomingCallCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Properties.PREF_INCOMING_CALL_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(incomingCallCheckbox);
			}
		});
    	
        incomingCallCheckbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = preferences.getBoolean(Properties.PREF_INCOMING_CALL_VALUE, false);
				if(incomingCallCheckbox != null){
					incomingCallCheckbox.setChecked(mValue);
				}
				
			}
		});
    	
    	incomingTextCheckbox = (SwitchCompat)v.findViewById(R.id.switch_incoming_sms);
        incomingTextCheckbox.setChecked(preferences.getBoolean(Properties.PREF_INCOMING_TEXT_VALUE, true));
        CommonUtils.setSwitchChangeColor(incomingTextCheckbox);
        incomingTextCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Properties.PREF_INCOMING_TEXT_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(incomingTextCheckbox);
			}
		});
    	
        incomingTextCheckbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = preferences.getBoolean(Properties.PREF_INCOMING_TEXT_VALUE, false);
				if(incomingTextCheckbox != null){
					incomingTextCheckbox.setChecked(mValue);
				}
				
			}
		});
    	
        normalCheckbox = (SwitchCompat)v.findViewById(R.id.switch_normal_mode);
        normalCheckbox.setChecked(preferences.getBoolean(Properties.PREF_NORMAL_MODE_VALUE, true));
        CommonUtils.setSwitchChangeColor(normalCheckbox);
        normalCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Properties.PREF_NORMAL_MODE_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(normalCheckbox);
			}
		});
    	
    	normalCheckbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = preferences.getBoolean(Properties.PREF_NORMAL_MODE_VALUE, false);
				if(normalCheckbox != null){
					normalCheckbox.setChecked(mValue);
				}
				
			}
		});
    	
        vibrateCheckbox = (SwitchCompat)v.findViewById(R.id.switch_vibrate_mode);
        vibrateCheckbox.setChecked(preferences.getBoolean(Properties.PREF_VIBRATE_MODE_VALUE, false));
        CommonUtils.setSwitchChangeColor(vibrateCheckbox);
        vibrateCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Properties.PREF_VIBRATE_MODE_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(vibrateCheckbox);
			}
		});
    	
    	vibrateCheckbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = preferences.getBoolean(Properties.PREF_VIBRATE_MODE_VALUE, false);
				if(vibrateCheckbox != null){
					vibrateCheckbox.setChecked(mValue);
				}

			}
		});
        
        silentCheckbox = (SwitchCompat)v.findViewById(R.id.switch_silent_mode);
        silentCheckbox.setChecked(preferences.getBoolean(Properties.PREF_SILENT_MODE_VALUE, false));
        CommonUtils.setSwitchChangeColor(silentCheckbox);
        silentCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Properties.PREF_SILENT_MODE_VALUE, isChecked);
				editor.commit();
				CommonUtils.setSwitchChangeColor(silentCheckbox);
			}
		});
    	
    	silentCheckbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean mValue = preferences.getBoolean(Properties.PREF_SILENT_MODE_VALUE, false);
				if(silentCheckbox != null){
					silentCheckbox.setChecked(mValue);
				}
			
			}
		});
        
        incomingCall.setOnClickListener(this);
        incomingText.setOnClickListener(this);
        normalMode.setOnClickListener(this);
        vibrateMode.setOnClickListener(this);
        silentMode.setOnClickListener(this);
             
        return v;
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_incoming_call:
			showIncomingCallDialog(getFragmentManager());	
			break;
		case R.id.rl_incoming_sms:
			showIncomingTextDialog(getFragmentManager());
			break;
		case R.id.rl_normal_mode:
			saveValuePreferences(normalCheckbox, Properties.PREF_NORMAL_MODE_VALUE);
			break;
		case R.id.rl_vibrate_mode:
			saveValuePreferences(vibrateCheckbox, Properties.PREF_VIBRATE_MODE_VALUE);
			break;
		case R.id.rl_silent_mode:
			saveValuePreferences(silentCheckbox, Properties.PREF_SILENT_MODE_VALUE);
			break;
		default:
			break;
			
		}
		
	}
    
    private void checkBoxChecked(final SwitchCompat mCheckbox, final String propertyKey) {
    	
		
	}

	public void showIncomingCallDialog(FragmentManager manager){
    	mCallDialog = new IncomingCallDialogFragment();
    	mCallDialog.show(manager, null);
    }
    
    public void showIncomingTextDialog(FragmentManager manager){
    	mTextDialog = new IncomingTextDialogFragment();
    	mTextDialog.show(manager, null);
    }
    
    public void saveValuePreferences(final SwitchCompat mCheckbox, final String propertyKey){
    	boolean value = !preferences.getBoolean(propertyKey, false);
    	mCheckbox.setChecked(value);
    	SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(propertyKey, value);
		editor.commit();
    }
    
}