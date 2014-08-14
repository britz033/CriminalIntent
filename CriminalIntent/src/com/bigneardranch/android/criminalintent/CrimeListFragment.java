package com.bigneardranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	private static final int REQUEST_CRIME = 1003;
	private static final String TAG = "CrimeListFragment"; 
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		getActivity().setTitle(R.string.crime_title);
		
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_item_new_crime :
			Crime c = new Crime();
			CrimeLab.get(getActivity()).addCrime(c);
			Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
			startActivity(intent);
			return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		
		Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivityForResult(intent, REQUEST_CRIME);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CRIME){
			
		}
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime>{
		public CrimeAdapter(ArrayList<Crime> crimes){
			super(getActivity(),0,crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
				convertView = getActivity().getLayoutInflater()
				.inflate(R.layout.list_item_crime, null);
			
			Crime c = getItem(position);
			
			TextView titleText = (TextView) convertView.findViewById(R.id.txt_crime_list_item_title);
			titleText.setText(c.getTitle());
			TextView dateText = (TextView) convertView.findViewById(R.id.txt_crime_list_item_date);
			dateText.setText(c.getSimpleDate());
			CheckBox solvedCBox = (CheckBox) convertView.findViewById(R.id.cbox_crime_list_item_solved);
			solvedCBox.setChecked(c.isSolved());
			return convertView;
		}
	}
	
	
	/** 
	 * <p>액티비티가 onResume 호출시 같이 호출된다<br> 그때 리스트를 다시 로딩하여
	 * 변경사항 반영</p>
	 * @see android.support.v4.app.Fragment#onResume()
	 * @param 없음 걍 리줌이니까
	 * @return 없음 역시나..
	 * <ul><li>1번</li><li>2번</li></ul>
	 * 
	 */
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
}
