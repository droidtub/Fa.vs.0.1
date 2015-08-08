package com.example.flashalert.view;

import com.example.flashalert.R;
import com.example.flashalert.utils.Properties;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class IncomingCallDialogFragment extends DialogFragment implements OnSeekBarChangeListener{

	private int onProgress = 0;
	private int offProgress = 0;
	
	private SharedPreferences pref;
	private SharedPreferences.Editor edit;
	private TextView onLength;
	private TextView offLength;
	@Override
    public Dialog onCreateDialog(Bundle bundle){
		pref = getActivity().getSharedPreferences(Properties.PREF_MAIN_NAME, Context.MODE_PRIVATE);
		edit = pref.edit();
		
		AlertDialog.Builder callBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater callInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = callInflater.inflate(R.layout.call_dialog_layout, null);
		SeekBar mSeekBarOnLength = (SeekBar) view.findViewById(R.id.seekbar_on_length);
		mSeekBarOnLength.setOnSeekBarChangeListener(this);
		SeekBar mSeekBarOffLength = (SeekBar)view.findViewById(R.id.seekbar_off_length);
		mSeekBarOffLength.setOnSeekBarChangeListener(this);
		onLength = (TextView)view.findViewById(R.id.on_length_ms);
	    offLength = (TextView)view.findViewById(R.id.off_length_ms);
		mSeekBarOnLength.setProgress(pref.getInt(Properties.PREF_CALL_ON_LENGTH_VALUE, 0));
		mSeekBarOffLength.setProgress(pref.getInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, 0));
		onLength.setText(pref.getInt(Properties.PREF_CALL_ON_LENGTH_VALUE, 0) + " ms");
		offLength.setText(pref.getInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, 0) + " ms");
		callBuilder.setView(view)
				.setTitle(R.string.call_title)
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
		
		AlertDialog dialog = callBuilder.create();
		return dialog;
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.seekbar_on_length:
			onProgress = progress;
			onLength.setText(onProgress + " ms");
			break;
		case R.id.seekbar_off_length:
			offProgress = progress;
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
		switch (seekBar.getId()) {
		case R.id.seekbar_on_length:
			edit.putInt(Properties.PREF_CALL_ON_LENGTH_VALUE, onProgress);
			edit.commit();
			break;
		case R.id.seekbar_off_length:
			edit.putInt(Properties.PREF_CALL_OFF_LENGTH_VALUE, offProgress);
			edit.commit();
		default:
			break;
		}
		
	}
    
}
