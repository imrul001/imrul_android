package com.example.bdrailwayticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.bdrailwayticket.adapter.StationArrayAdapter;
import com.example.bdrailwayticket.ld.api.domain.SeatClass;
import com.example.bdrailwayticket.ld.api.domain.Services;
import com.example.bdrailwayticket.ld.api.domain.Station;
import com.example.bdrailwayticket.ld.api.domain.Train;
import com.example.bdrailwayticket.listener.SpinnerOnItemSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.google.ads.*;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.activity_dashboard);
		Spinner spinnerFromStations = (Spinner) findViewById(R.id.spinnerFromStation);
		spinnerFromStations.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		

		List<Date> journeyDates = Services.getInstance().getStationService()
				.getJourneyDate();
		Spinner spinnerJourneyDates = (Spinner) findViewById(R.id.spinnerJourneyDate);
		List<String> strJourneyDates = new ArrayList<String>();
		for (Date date : journeyDates) {
			strJourneyDates.add(date.toString());
		}
		ArrayAdapter<String> adapterJourneyDate = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				strJourneyDates);
		spinnerJourneyDates.setAdapter(adapterJourneyDate);

		List<Station> fromStations = Services.getInstance().getStationService()
				.getFromStation();
		
		List<String> strFromStations = new ArrayList<String>();
		for (Station station : fromStations) {
			strFromStations.add(station.getName());
		}
//		StationArrayAdapter adapterFSS = new StationArrayAdapter(
//				getApplicationContext(), android.R.layout.simple_spinner_item,
//				fromStations);
		//spinnerFromStations.setAdapter(adapterFSS);

		Station testFromStation = new Station();
		testFromStation.setCode("DA");

		List<Station> toStations = Services.getInstance().getStationService()
				.getToStation(testFromStation);
		Spinner spinnerToStations = (Spinner) findViewById(R.id.spinnerToStation);
		List<String> strToStations = new ArrayList<String>();
		for (Station station : toStations) {
			strToStations.add(station.getName());
		}
		ArrayAdapter<String> adapterTSS = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				strToStations);
		spinnerToStations.setAdapter(adapterTSS);

		Station testToStation = new Station();
		testToStation.setCode("CTG");

		List<SeatClass> seatClasses = Services.getInstance()
				.getStationService()
				.getAvailableClass(testFromStation, testToStation);
		Spinner spinnerSeatClasses = (Spinner) findViewById(R.id.spinnerSeatClass);
		List<String> strSeatClasses = new ArrayList<String>();
		for (SeatClass seatClass : seatClasses) {
			strSeatClasses.add(seatClass.getName());
		}
		ArrayAdapter<String> adapterSeatClasses = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				strSeatClasses);
		spinnerSeatClasses.setAdapter(adapterSeatClasses);

		SeatClass testSeatClass = new SeatClass();
		testSeatClass.setCode("SULOV");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date journeyDate = new Date();
		try {
			journeyDate = sdf.parse("11-10-2013");
		} catch (Exception ex) {

		}

		List<Train> trains = Services.getInstance()
				.getStationService()
				.searchTrain(journeyDate, testFromStation, testToStation,
						testSeatClass);
		System.out.println(trains.size());
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	public void doSearch(View view) {

	}

}
