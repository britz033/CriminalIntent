package com.bigneardranch.android.criminalintent;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class SelectDialogFragment extends DialogFragment {
	
	private static final String EXTRA_DATE = "select_extra_date";
	private static final String TAG_DATE = "TAG_DATE";
	private static final String TAG_TIME = "TAG_TIME";
	private Date mDate;
	
	public static SelectDialogFragment newInstance(Date date){
		SelectDialogFragment fragment = new SelectDialogFragment();
		
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		return new AlertDialog.Builder(getActivity())
		.setTitle(R.string.select_dialog_title)
		.setPositiveButton(R.string.select_dialog_date, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatePickerFragment fragment = DatePickerFragment.newInstance(mDate);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fragment.setTargetFragment(getTargetFragment(), CrimeFragment.REQUEST_DATE);
				fragment.show(fm, TAG_DATE);
			}
		})
		.setNegativeButton(R.string.select_dialog_time, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TimePickerFragment fragment = TimePickerFragment.newInstance(mDate);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fragment.setTargetFragment(getTargetFragment(), CrimeFragment.REQUEST_TIME);
				fragment.show(fm, TAG_TIME);
			}
		})
		.create();
	}
}
