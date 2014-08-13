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
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	
	public static final String EXTRA_DATE= "date_key";
	private Date mDate;
	
	public static DatePickerFragment newInstance(Date date){
		DatePickerFragment dpf = new DatePickerFragment();
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		dpf.setArguments(args);
		return dpf;
	}
	
	private void sendResult(int resultCode){
		if(getTargetFragment() == null)	// 타겟프래그먼트가 없으면 연결이고 뭐고 없으므로 아웃
			return;
		
		Intent intent = new Intent();
		intent.putExtra(EXTRA_DATE, mDate);
		
		getTargetFragment()
		.onActivityResult(getTargetRequestCode(), resultCode, intent);
		
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		DatePicker dp = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
		dp.init(year, month, day, new OnDateChangedListener() {
			
			// 장치회전시를 대비하여 날짜를 변경한 상태도 저장 해놓는다
			// 이러면 회전이후에도 자신이 변경한 날짜가 초기화 되지 않고 그대로 디스플레이된다
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				mDate = new GregorianCalendar(year,month,day).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});
		
		
		return new AlertDialog.Builder(getActivity())
		.setTitle(R.string.date_picker_title)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);
			}
		})
		.setView(v)
		.create();
	}
}
