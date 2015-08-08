package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.utils.Properties;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class IncomingTextDialogFragment extends DialogFragment implements OnSeekBarChangeListener{
	
	private int onProgress = 0;
	private int offProgress = 0;
	private int times = 0;
	
	private SharedPreferences pref;
	private SharedPreferences.Editor edit;
	private TextView onLength;
	private TextView offLength;
	private TextView flashTimes;
	@Override
	public Dialog onCreateDialog(Bundle bundle){
		pref = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		edit = pref.edit();
		
		AlertDialog.Builder txtBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater txtInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = txtInflater.inflate(R.layout.txt_dialog_layout, null);
		
		SeekBar mSeekBarOnLength = (SeekBar) view.findViewById(R.id.text_seekbar_on_length);
		mSeekBarOnLength.setOnSeekBarChangeListener(this);
		SeekBar mSeekBarOffLength = (SeekBar)view.findViewById(R.id.text_seekbar_off_length);
		mSeekBarOffLength.setOnSeekBarChangeListener(this);
		SeekBar mSeekBarFlashTimes = (SeekBar)view.findViewById(R.id.text_seekbar_flash_time);
		mSeekBarFlashTimes.setOnSeekBarChangeListener(this);
		
		onLength = (TextView)view.findViewById(R.id.text_on_length_ms);
	    offLength = (TextView)view.findViewById(R.id.text_off_length_ms);
	    flashTimes = (TextView)view.findViewById(R.id.text_flash_time);
	    
		mSeekBarOnLength.setProgress(pref.getInt(Properties.PREF_TXT_ON_LENGTH_VALUE, 0));
		mSeekBarOffLength.setProgress(pref.getInt(Properties.PREF_TXT_OFF_LENGTH_VALUE, 0));
		mSeekBarFlashTimes.setProgress(pref.getInt(Properties.PREF_TXT_TIMES_VALUE, 0));
		
		onLength.setText(pref.getInt(Properties.PREF_TXT_ON_LENGTH_VALUE, 0) + " ms");
		offLength.setText(pref.getInt(Properties.PREF_TXT_OFF_LENGTH_VALUE, 0) + " ms");
		flashTimes.setText(pref.getInt(Properties.PREF_TXT_TIMES_VALUE, 0) + " Time(s)");
		
		txtBuilder.setView(view)
				.setTitle(R.string.text_title)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setNeutralButton(R.string.test_on, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
		Dialog dialog = txtBuilder.create();
		return dialog;
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.text_seekbar_on_length:
			onProgress = progress;
			onLength.setText(onProgress + " ms");
			break;
		case R.id.text_seekbar_off_length:
			offProgress = progress;
			offLength.setText(offProgress + " ms");
			break;
		case R.id.text_seekbar_flash_time:
			if (seekBar.getProgress() < 1) {
				progress = 1;
				seekBar.setProgress(1);
			}
			times = progress;
			flashTimes.setText(times + " Time(s)");
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
		switch (seekBar.getId()) {
		case R.id.text_seekbar_on_length:
			edit.putInt(Properties.PREF_TXT_ON_LENGTH_VALUE, onProgress);
			edit.commit();
			break;
		case R.id.text_seekbar_off_length:
			edit.putInt(Properties.PREF_TXT_OFF_LENGTH_VALUE, offProgress);
			edit.commit();
			break;
		case R.id.text_seekbar_flash_time:
			if (seekBar.getProgress() < 1) {
				seekBar.setProgress(1);
			}
			edit.putInt(Properties.PREF_TXT_TIMES_VALUE, times);
			edit.commit();
			break;
		default:
			break;
		}
		
	}
}
