package com.example.bdrailwayticket.ld.api.domain;

import android.app.Application;

public class ApplicationSettings extends Application {
	private String userName;
	private String password;
	private String sessionId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
