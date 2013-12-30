package com.example.bdrailwayticket.ld.api.domain;

import java.util.Date;

public class Ticket {
	private String messageId;
	private String mobileNumber;
	private String outId;
	private Date journeyDate;
	private Train train;
	private Double fair;
	private Double vat;
	private Station fromStation;
	private Station toStation;
	private SeatClass seatClass;
	private int adult;
	private int child;
	private Double bankCharge;

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public Double getBankCharge() {
		return bankCharge;
	}

	public void setBankCharge(Double bankCharge) {
		this.bankCharge = bankCharge;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Double getFair() {
		return fair;
	}

	public Station getFromStation() {
		return fromStation;
	}

	public void setFromStation(Station fromStation) {
		this.fromStation = fromStation;
	}

	public Station getToStation() {
		return toStation;
	}

	public void setToStation(Station toStation) {
		this.toStation = toStation;
	}

	public void setFair(Double fair) {
		this.fair = fair;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}
}
