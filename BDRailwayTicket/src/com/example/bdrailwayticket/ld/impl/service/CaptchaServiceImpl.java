package com.example.bdrailwayticket.ld.impl.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.bdrailwayticket.ld.api.domain.InfoURI;
import com.example.bdrailwayticket.ld.api.service.CaptchaService;

public class CaptchaServiceImpl implements CaptchaService {
	private HttpClient httpclient = new DefaultHttpClient();

	@Override
	public Map<String, String> getCaptchaInfo() {
		Map<String, String> captchaInfo = new HashMap<String, String>(); 
		HttpGet httpget = new HttpGet(InfoURI.BASE_URI);
		HttpParams params = httpclient.getParams();
		HttpClientParams.setRedirecting(params, false);
		String phpSessionId = "";
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);						
		} catch (Exception e) {		
		}
		for (Header header : response.getHeaders("Set-Cookie")) {
			phpSessionId = header.getValue();
		}
		if (StringUtil.isBlank(phpSessionId)) {
			throw new RuntimeException("Session information not found");
		}
		phpSessionId = phpSessionId.split(";")[0];
		InfoURI.SESSIONID = phpSessionId;

		String html = getFrontPageHtml(response);
		captchaInfo = getValuesFromHtml(html);
		return captchaInfo;
	}
	private String getFrontPageHtml(HttpResponse response) {
		String html = "";
		try {
			html = EntityUtils.toString(response.getEntity());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return html;
	}

	private Map<String, String> getValuesFromHtml(String html) {
		Map<String, String> valueMap = new HashMap<String, String>();
		Document doc = Jsoup.parse(html);
		valueMap.put("captchaCode", getCaptchaCode(doc));
		valueMap.put("captchaUrl", getCaptchaUrl(doc));
		return valueMap;
	}

	private String getCaptchaCode(Document doc) {
		String captchaCode = "";
		Element element = doc.getElementById("captcha_code");
		captchaCode = element.getElementsByAttribute("value").val();
		return captchaCode;
	}

	private String getCaptchaUrl(Document doc) {
		String captchaUrl = "";
		Element element = doc.getElementById("captcha");
		captchaUrl = element.attr("src");		
		return captchaUrl;
	}

}
