package com.example.bdrailwayticket.ld.api.service;

import java.util.Date;
import java.util.List;

import com.example.bdrailwayticket.ld.api.domain.SeatClass;
import com.example.bdrailwayticket.ld.api.domain.Station;
import com.example.bdrailwayticket.ld.api.domain.Ticket;
import com.example.bdrailwayticket.ld.api.domain.Train;

public interface StationService {

	public List<Date> getJourneyDate();

	public List<Station> getFromStation();

	public List<Station> getToStation(Station fromStation);

	public List<SeatClass> getAvailableClass(Station fromStation,
			Station toStation);

	public List<Train> searchTrain(Date journeyDate, Station fromStation,
			Station toStation, SeatClass seatClass);

	public Ticket searchTicket(Date journeyDate, Train train, Station fromStation,
			Station toStation, SeatClass seatClass, int adult, int child);
	
	public boolean bookTicket(Ticket ticket, String paymentMethod);
}
