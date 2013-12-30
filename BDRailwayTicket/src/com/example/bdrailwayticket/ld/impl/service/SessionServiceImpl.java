package com.example.bdrailwayticket.ld.impl.service;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jsoup.helper.StringUtil;
import android.util.Log;

import com.example.bdrailwayticket.ld.api.domain.InfoURI;
import com.example.bdrailwayticket.ld.api.service.SessionService;

public class SessionServiceImpl implements SessionService {
	private HttpClient httpclient = new DefaultHttpClient();

	@Override
	public String retrieveSession() {
		HttpGet httpget = new HttpGet(InfoURI.BASE_URI);
		HttpParams params = httpclient.getParams();
		HttpClientParams.setRedirecting(params, false);

		// Execute the request
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("Praeda", response.getStatusLine().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String phpSessionId = "";
		for (Header header : response.getHeaders("Set-Cookie")) {
			phpSessionId = header.getValue();
		}
		if (StringUtil.isBlank(phpSessionId)) {
			throw new RuntimeException("Session information not found");
		}
		phpSessionId = phpSessionId.split(";")[0];
		InfoURI.SESSIONID = phpSessionId;
		return phpSessionId;
	}

}
