package com.example.bdrailwayticket.adapter;

import java.util.ArrayList;

import com.example.bdrailwayticket.ld.api.domain.Station;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StationArrayAdapter extends ArrayAdapter<Station> {
	private Activity activity;
	ArrayList<Station> data = null;

	public StationArrayAdapter(Activity activity, int resource,
			ArrayList<Station> data) {
		super(activity, resource, data);
		this.activity = activity;
		this.data = data;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);

		if (v == null) {
			v = new TextView(this.activity);
		}
		v.setTextColor(Color.BLACK);
		v.setText(data.get(position).getName());
		return v;
	}

	@Override
	public Station getItem(int position) {
		return data.get(position);
	}

}
