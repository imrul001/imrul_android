package com.example.bdrailwayticket.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerOnItemSelectedListener implements OnItemSelectedListener{

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String selectedItem = (String)arg0.getSelectedItem();
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}

}
