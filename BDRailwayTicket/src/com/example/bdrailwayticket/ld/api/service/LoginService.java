package com.example.bdrailwayticket.ld.api.service;

public interface LoginService {
	public boolean login(String username, String password, String captchaString, String captchaId);
}
