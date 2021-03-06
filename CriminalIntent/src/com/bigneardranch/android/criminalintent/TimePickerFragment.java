package com.bigneardranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	
	public static final String EXTRA_TIME = "extra-TIME";
	private Date mDate;
	
	public static TimePickerFragment newInstance(Date date){
		TimePickerFragment fragment = new TimePickerFragment();
		
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, date);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void sendResult(int resultCode){
		if(getTargetFragment() == null)
			return;
		Intent intent = new Intent();
		intent.putExtra(EXTRA_TIME, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		
		mDate = (Date) getArguments().getSerializable(EXTRA_TIME);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_timePicker);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minuteOfHour) {
				mDate = new GregorianCalendar(year, month, day, hourOfDay, minuteOfHour).getTime();
				getArguments().putSerializable(EXTRA_TIME, mDate);
			}
		});
		
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
		.setTitle(R.string.time_picker_title)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);
			}
		})
		.setView(v)
		.create();
		
		return dialog;
	}
}
