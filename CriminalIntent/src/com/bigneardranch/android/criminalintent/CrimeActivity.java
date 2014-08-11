package com.bigneardranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class CrimeActivity extends SingleFragmentActivity {

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_fragment);
//		
//		FragmentManager fm = getSupportFragmentManager();
//		
//		// 프래그먼트 매니저는 리스트를 장치회전등으로 액티비티 소멸때도 가지고 있으므로
//		// 이미 가지고 있는지 확인과정이 필요하다
//		Fragment fragment = fm.findFragmentById(R.id.lay_fragmentContainer);
//		if(fragment==null){
//			fragment = new CrimeFragment();
//			
//			// 트랜잭션에 추가하는 것이 아닌 add 트랙잭션을 행함에 있어서의
//			// 정보를 프래그먼트 매니저에 전달한다. 
//			// r.id~ 이 리소스id에 프래그먼트 뷰가 나타나야할지를 매니저에 알려준다
//			fm.beginTransaction().add(R.id.lay_fragmentContainer, fragment)
//			.commit();
//		}
//	}

	@Override
	protected Fragment createFragment() {
		return new CrimeFragment();
	}

}
