package com.example.bdrailwayticket.ld.impl.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.example.bdrailwayticket.ld.api.domain.InfoURI;
import com.example.bdrailwayticket.ld.api.domain.SeatClass;
import com.example.bdrailwayticket.ld.api.domain.Station;
import com.example.bdrailwayticket.ld.api.domain.Ticket;
import com.example.bdrailwayticket.ld.api.domain.Train;
import com.example.bdrailwayticket.ld.api.service.StationService;

public class StationServiceImpl implements StationService {
	HttpClient client = new DefaultHttpClient();

	public StationServiceImpl() {
		client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
	}

	@Override
	public List<Station> getFromStation() {
		List<Station> stations = new ArrayList<Station>();
		HttpGet get = new HttpGet(InfoURI.PURCHASE_TICKET_FROM_STATION_URI);
		get.addHeader("Cookie", InfoURI.SESSIONID);
		try {
			HttpResponse response = client.execute(get);
			String html = EntityUtils.toString(response.getEntity());
			stations = getFromStationFromHtml(html);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stations;
	}

	private List<Station> getFromStationFromHtml(String html) {
		List<Station> stations = new ArrayList<Station>();
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("station_from");
		Elements options = element.getElementsByTag("option");
		for (Element option : options) {
			Station station = new Station();
			station.setCode(option.getElementsByAttribute("value").val());
			station.setName(option.text());
			stations.add(station);
		}
		return stations;
	}

	@Override
	public List<Station> getToStation(Station fromStation) {
		List<Station> stations = new ArrayList<Station>();
		HttpPost post = new HttpPost(InfoURI.GET_STATION_TO_URI);
		post.addHeader("Cookie", InfoURI.SESSIONID);
		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues.add(new BasicNameValuePair("stn_from", fromStation
				.getCode()));
		paramValues.add(new BasicNameValuePair("type", "TR"));
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(post);
			stations = getToStationFromHtml(EntityUtils.toString(response
					.getEntity()));

		} catch (Exception ex) {

		}
		return stations;
	}

	private List<Station> getToStationFromHtml(String html) {
		List<Station> stations = new ArrayList<Station>();
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("station_to");
		Elements options = element.getElementsByTag("option");
		for (Element option : options) {
			Station station = new Station();
			station.setCode(option.getElementsByAttribute("value").val());
			station.setName(option.text());
			stations.add(station);
		}
		return stations;
	}

	@Override
	public List<Date> getJourneyDate() {
		List<Date> journeyDates = new ArrayList<Date>();
		HttpGet get = new HttpGet(InfoURI.PURCHASE_TICKET_FROM_STATION_URI);
		get.addHeader("Cookie", InfoURI.SESSIONID);
		try {
			HttpResponse response = client.execute(get);
			String html = EntityUtils.toString(response.getEntity());
			journeyDates = getJourneyDateFromHtml(html);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return journeyDates;
	}

	private List<Date> getJourneyDateFromHtml(String html) {
		List<Date> journeyDates = new ArrayList<Date>();
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("journey_date");
		Elements options = element.getElementsByTag("option");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (Element option : options) {
			String date = option.getElementsByAttribute("value").val();
			Date dtDate = new Date();
			try {
				dtDate = sdf.parse(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			journeyDates.add(dtDate);
		}
		return journeyDates;
	}

	@Override
	public List<SeatClass> getAvailableClass(Station fromStation,
			Station toStation) {
		List<SeatClass> seatClasses = new ArrayList<SeatClass>();
		HttpPost post = new HttpPost(InfoURI.GET_SEAT_CLASS_URI);
		post.addHeader("Cookie", InfoURI.SESSIONID);
		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues.add(new BasicNameValuePair("stn_from", fromStation
				.getCode()));
		paramValues.add(new BasicNameValuePair("stn_to", toStation.getCode()));
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(post);
			seatClasses = getSeatClassFromHtml(EntityUtils.toString(response
					.getEntity()));

		} catch (Exception ex) {

		}
		return seatClasses;
	}

	private List<SeatClass> getSeatClassFromHtml(String html) {
		List<SeatClass> seatClasses = new ArrayList<SeatClass>();
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("route_class");
		Elements options = element.getElementsByTag("option");
		for (Element option : options) {
			SeatClass seatClass = new SeatClass();
			seatClass.setCode(option.getElementsByAttribute("value").val());
			seatClass.setName(option.text());
			seatClasses.add(seatClass);
		}
		return seatClasses;
	}

	@Override
	public List<Train> searchTrain(Date journeyDate, Station fromStation,
			Station toStation, SeatClass seatClass) {
		List<Train> trains = new ArrayList<Train>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strJourneyDate = sdf.format(journeyDate);
		HttpPost post = new HttpPost(InfoURI.SEARCH_TRAIN_URI);
		post.addHeader("Cookie", InfoURI.SESSIONID);
		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues.add(new BasicNameValuePair("journey_date", strJourneyDate));
		paramValues.add(new BasicNameValuePair("station_from", fromStation
				.getCode()));
		paramValues.add(new BasicNameValuePair("station_to", toStation
				.getCode()));
		paramValues.add(new BasicNameValuePair("route_class", seatClass
				.getCode()));
		paramValues.add(new BasicNameValuePair("purchase_ticket",
				"Search Train"));

		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(post);
			trains = getTrainsFromHtml(EntityUtils.toString(response
					.getEntity()));

		} catch (Exception ex) {

		}
		return trains;
	}

	private List<Train> getTrainsFromHtml(String html) {
		List<Train> trains = new ArrayList<Train>();
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("train_route_div_b");
		Elements trELements = element.getElementsByTag("tr");
		return trains;
	}

	@Override
	public Ticket searchTicket(Date journeyDate, Train train,
			Station fromStation, Station toStation, SeatClass seatClass,
			int adult, int child) {
		Ticket ticket = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strJourneyDate = sdf.format(journeyDate);
		HttpPost post = new HttpPost(InfoURI.SEARCH_TICKET_URI);
		post.addHeader("Cookie", InfoURI.SESSIONID);
		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues.add(new BasicNameValuePair("j_date", strJourneyDate));
		paramValues.add(new BasicNameValuePair("trn_no", train.getNumber()));
		paramValues.add(new BasicNameValuePair("stn_from", fromStation
				.getCode()));
		paramValues.add(new BasicNameValuePair("stn_to", toStation.getCode()));
		paramValues
				.add(new BasicNameValuePair("trn_class", seatClass.getCode()));
		paramValues.add(new BasicNameValuePair("adult", String.valueOf(adult)));
		paramValues.add(new BasicNameValuePair("child", String.valueOf(child)));

		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(post);
			ticket = getTicketFromHtml(EntityUtils.toString(response
					.getEntity()));

		} catch (Exception ex) {

		}
		return ticket;
	}

	private Ticket getTicketFromHtml(String html) {
		Ticket ticket = null;
		Document doc = Jsoup.parse(html);
		// check ticket available or not
		Element element = doc.getElementById("frm_book");
		if (element != null) {
			Double fair = Double.parseDouble(doc.getElementById("ofare")
					.getElementsByAttribute("value").text());
			Double vat = Double.parseDouble(doc.getElementById("ovat")
					.getElementsByAttribute("value").text());
			Double bankCharge = Double.parseDouble(doc
					.getElementById("oscharge").getElementsByAttribute("value")
					.text());
			String stationFromCode = doc.getElementById("station_from")
					.getElementsByAttribute("value").text();
			String stationFromName = doc.getElementById("stn_frm")
					.getElementsByAttribute("value").text();
			String stationToCode = doc.getElementById("station_to")
					.getElementsByAttribute("value").text();
			String stationToName = doc.getElementById("stn_to")
					.getElementsByAttribute("value").text();
			String journeyDate = doc.getElementById("ojrny_date")
					.getElementsByAttribute("value").text();
			String seatClassCode = doc.getElementById("class")
					.getElementsByAttribute("value").text();
			String adultSeat = doc.getElementById("adult_seat")
					.getElementsByAttribute("value").text();
			String childSeat = doc.getElementById("child_seat")
					.getElementsByAttribute("value").text();
			String trainNo = doc.getElementById("train_no")
					.getElementsByAttribute("value").text();
			String trainName = doc.getElementById("trn_no")
					.getElementsByAttribute("value").text();

			Station fromStation = new Station();
			fromStation.setCode(stationFromCode);
			fromStation.setName(stationFromName);

			Station toStation = new Station();
			toStation.setCode(stationToCode);
			toStation.setName(stationToName);

			SeatClass seatClass = new SeatClass();
			seatClass.setCode(seatClassCode);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			Date dtJourneyDate = null;
			try {
				dtJourneyDate = sdf.parse(journeyDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Train train = new Train();
			train.setName(trainName);
			train.setNumber(trainNo);

			// set the value in the ticket
			ticket.setAdult(Integer.valueOf(adultSeat));
			ticket.setBankCharge(bankCharge);
			ticket.setChild(Integer.valueOf(childSeat));
			ticket.setFair(fair);
			ticket.setFromStation(fromStation);
			ticket.setJourneyDate(dtJourneyDate);
			ticket.setToStation(toStation);
			ticket.setTrain(train);

		} else {
			// mobile quota seats not available
		}

		return ticket;
	}

	@Override
	public boolean bookTicket(Ticket ticket, String paymentMethod) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		SimpleDateFormat sdfWithoutTime = new SimpleDateFormat("dd-MM-yyyy");
		String strJourneyDateWithoutTime = sdfWithoutTime.format(ticket
				.getJourneyDate());
		String strJourneyDate = sdf.format(ticket.getJourneyDate());

		HttpPost post = new HttpPost(InfoURI.SEARCH_TICKET_URI);
		post.addHeader("Cookie", InfoURI.SESSIONID);
		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues
				.add(new BasicNameValuePair("msg_id", ticket.getMessageId()));
		paramValues.add(new BasicNameValuePair("mobile", ticket
				.getMobileNumber()));
		paramValues.add(new BasicNameValuePair("out_id", ticket.getOutId()));
		paramValues.add(new BasicNameValuePair("journey_date",
				strJourneyDateWithoutTime));
		paramValues.add(new BasicNameValuePair("route_class", ticket
				.getSeatClass().getCode()));
		paramValues.add(new BasicNameValuePair("ojrny_date", strJourneyDate));
		paramValues.add(new BasicNameValuePair("class", ticket.getSeatClass()
				.getCode()));
		paramValues.add(new BasicNameValuePair("ofare", ticket.getFair()
				.toString()));
		paramValues.add(new BasicNameValuePair("stn_from", ticket
				.getFromStation().getName()));
		paramValues.add(new BasicNameValuePair("station_from", ticket
				.getFromStation().getCode()));
		paramValues.add(new BasicNameValuePair("adult_seat", String
				.valueOf(ticket.getAdult())));
		paramValues.add(new BasicNameValuePair("ovat", ticket.getVat()
				.toString()));
		paramValues.add(new BasicNameValuePair("stn_to", ticket.getToStation()
				.getName()));
		paramValues.add(new BasicNameValuePair("station_to", ticket
				.getToStation().getCode()));
		paramValues.add(new BasicNameValuePair("child_seat", String
				.valueOf(ticket.getChild())));
		paramValues.add(new BasicNameValuePair("oscharge", ticket
				.getBankCharge().toString()));
		paramValues.add(new BasicNameValuePair("trn_no", ticket.getTrain()
				.getName()));
		paramValues.add(new BasicNameValuePair("train_no", ticket.getTrain()
				.getNumber()));
		paramValues.add(new BasicNameValuePair("otot_amt", String
				.valueOf(ticket.getFair() + ticket.getBankCharge()
						+ ticket.getVat())));
		if (paymentMethod == "DBBL") {
			paramValues.add(new BasicNameValuePair("book_dbbl",
					"Purchase By DBBL"));
		} else {
			paramValues.add(new BasicNameValuePair("book_brac",
					"Purchase By BRAC"));
		}

		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(post);
			String location;
			if (response.getStatusLine().getStatusCode() == 302) {
				location = response.getFirstHeader("Location").getValue();
				return true;
			}
		} catch (Exception ex) {

		}

		return false;
	}

}
