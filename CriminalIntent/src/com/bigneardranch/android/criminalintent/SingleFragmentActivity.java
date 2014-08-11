package com.bigneardranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	
	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_fragment);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.lay_fragmentContainer);
		if(fragment == null){
			fragment = createFragment();
			fm.beginTransaction()
				.add(R.id.lay_fragmentContainer,fragment)
				.commit();
		}
		
	}
	
	
}
