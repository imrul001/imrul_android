package com.example.bdrailwayticket.ld.impl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.util.Log;
import com.example.bdrailwayticket.ld.api.domain.InfoURI;
import com.example.bdrailwayticket.ld.api.service.LoginService;

public class LoginServiceImpl implements LoginService {
	private HttpClient client = new DefaultHttpClient();

	public LoginServiceImpl() {

	}

	@Override
	public boolean login(String username, String password,
			String captchaString, String captchaId) {
		boolean isAuthorized = false;
		client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);

		// Services.getInstance().getStationService().getFromStation();

		List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
		paramValues
				.add(new BasicNameValuePair("usermail", username));
		paramValues.add(new BasicNameValuePair("password", password));
		paramValues.add(new BasicNameValuePair("security_code", captchaString));
		paramValues.add(new BasicNameValuePair("captcha[id]", captchaId));
		paramValues.add(new BasicNameValuePair("signin", "SIGN-IN"));

		HttpPost httpPost = new HttpPost(InfoURI.LOGIN_POST_URI);
		
		httpPost.addHeader("Cookie", InfoURI.SESSIONID);		

		HttpResponse response = null;
		try {			
			httpPost.setEntity(new UrlEncodedFormEntity(paramValues));
			response = client.execute(httpPost);			
			// Examine the response status
			Log.i("Praeda", response.getStatusLine().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (response.getStatusLine().getStatusCode() == 302
				&& response.getFirstHeader("Location").getValue().equals("/account/index")) {
			return true;
		}

		return isAuthorized;
	}

}
