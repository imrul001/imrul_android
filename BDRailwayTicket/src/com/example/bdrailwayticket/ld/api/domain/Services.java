package com.example.bdrailwayticket.ld.api.domain;

import com.example.bdrailwayticket.ld.api.service.CaptchaService;
import com.example.bdrailwayticket.ld.api.service.LoginService;
import com.example.bdrailwayticket.ld.api.service.SessionService;
import com.example.bdrailwayticket.ld.api.service.StationService;
import com.example.bdrailwayticket.ld.impl.service.CaptchaServiceImpl;
import com.example.bdrailwayticket.ld.impl.service.LoginServiceImpl;
import com.example.bdrailwayticket.ld.impl.service.SessionServiceImpl;
import com.example.bdrailwayticket.ld.impl.service.StationServiceImpl;

public class Services {
	private CaptchaService captchaService;
	private LoginService loginService;
	private SessionService sessionService;
	private StationService stationService;
	private static Services services;
	
	private Services(){
		captchaService = new CaptchaServiceImpl();
		loginService = new LoginServiceImpl();
		sessionService = new SessionServiceImpl();
		stationService = new StationServiceImpl();
	}
	
	public CaptchaService getCaptchaService() {
		return captchaService;
	}
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public SessionService getSessionService() {
		return sessionService;
	}
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	public StationService getStationService() {
		return stationService;
	}
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}
	
	public static Services getInstance(){
		if(services == null){
			services = new Services(); 
		}
		return services;
	}	
}
