package com.bigneardranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	public static final String EXTRA_CRIME_ID = "extra_crime_id";
	private static final String DIALOG_SELECT = "SELECT";
	public static final int REQUEST_DATE = 30289;
	public static final int REQUEST_TIME = 334289;
	
	/**
	 * CrimeFragment의 인스턴트를 반환한다.
	 * 굳이 이런게 있는 이유는 CrimeFragment 생성과 동시에 매개변수를
	 * setArguments 로 전달하는 과정을 다른 곳에서 하지 않고
	 * 이 안에서 처리하기 위해서다
	 * @param crimeId 생성할 crime 데이터의 Id
	 * 	*/
	public static CrimeFragment newInstance(UUID crimeId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void updateDate(){
		mDateButton.setText(mCrime.getSimpleDate());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId); 
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, container, false);
		
		mTitleField = (EditText) v.findViewById(R.id.txt_crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCrime.setTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mDateButton = (Button)v.findViewById(R.id.btn_crime_date);
		updateDate();
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				SelectDialogFragment dialog = SelectDialogFragment.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, 0);
				dialog.show(fm, DIALOG_SELECT);
//				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
//				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
//				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.cbox_crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
		
		return v;
	}
	
	/* fragment도 startActivityForResult 나 onActivityResult 메소드를 가지고 있고
	 * 쓸 수도 있다. 그러나 종료시에 결과값을 가질 수 있는 것은 Activity 뿐이므로 
	 * setResult 는 없다 
	 * 그렇기에 호스팅하는 액티비티의 setResult에 결과값을 전달한다
	 * 
	 * 그럼 fragment의 onActivityResult는 뭔가? 아직 더 공부해봐야 알겠는데
	 * 일단 Activity.onActivityResult 가 호출받아진후에 프래그먼트의 onActivityResult가
	 * FragmentManager에 의해 불려진다
	 * 단,
	 * 같은 액티비티위에 호스팅된 프래그먼트들일 경우 다른 프래그먼트가 onActivityResult를 직접
	 * 호출할 수 있다(수동으로..-ㅅ-;) 
	 * 
	 * 다시 정리하면 프래그먼트의 onActivityResult는 액티비티가 startActivityForResult를 발동시켰을 경우
	 * 해당 activity가 정리되면 반드시 액티비티의 result후 같은 인자들 받아 호출된다(비록 아무 상관없다해도..)
	 * 또한 다른 프래그먼트끼리의 통신으로 사용할 수 있다
	 * 어차피 수동으로 부르는거 아무 메소드나 자신이 직접 정의해서 받아도 되지 않을까 하긴한데..
	 * setTargetFragment 라고 연결을 쉽게 해주는 메소드가 있고 그 메소드가
	 * onActivityResult 형식의 인자라서 굳이 따로 만들지 않고 그대로 onActivityResult를 사용하는거 같다
	 */
	public void returnResult(){
		getActivity().setResult(Activity.RESULT_OK, null);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == REQUEST_DATE){
				Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
				mCrime.setDate(date);
				updateDate();
			}
			if(requestCode == REQUEST_TIME){
				Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
				mCrime.setDate(date);
				updateDate();
			}
		}
	}
}
