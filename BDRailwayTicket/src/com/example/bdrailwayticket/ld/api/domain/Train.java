package com.example.bdrailwayticket.ld.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Train {
	private String name;
	private String number;
	private List<SeatClass> availableClasses;
	private Date departureTime;

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<SeatClass> getAvailableClasses() {
		if (availableClasses == null) {
			availableClasses = new ArrayList<SeatClass>();
		}
		return availableClasses;
	}

	public void setAvailableClasses(List<SeatClass> availableClasses) {
		this.availableClasses = availableClasses;
	}
}
