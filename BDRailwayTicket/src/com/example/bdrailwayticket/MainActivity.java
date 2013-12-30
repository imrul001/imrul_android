package com.example.bdrailwayticket;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.bdrailwayticket.ld.api.domain.ApplicationSettings;
import com.example.bdrailwayticket.ld.api.domain.InfoURI;
import com.example.bdrailwayticket.ld.api.domain.Services;

import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends Activity {

	private String captchaCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);

		Services.getInstance().getSessionService().retrieveSession();
		Map<String, String> values = Services.getInstance().getCaptchaService()
				.getCaptchaInfo();

		try {
			if (StringUtil.isBlank(values.get("captchaCode"))) {
				throw new RuntimeException("Can not get captcha code");
			}
			if (StringUtil.isBlank(values.get("captchaUrl"))) {
				throw new RuntimeException("Can not get captcha url");
			}
			this.captchaCode = values.get("captchaCode");

			URL url = new URL(InfoURI.BASE_URI + values.get("captchaUrl"));
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
			ImageView imageViewCaptcha = (ImageView) findViewById(R.id.imageViewCaptchaString);
			imageViewCaptcha.setImageBitmap(bmp);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void doLogin(View view) {
		EditText editTextUserName = (EditText) findViewById(R.id.editTextUserMail);
		EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		TextView textViewCaptchaString = (TextView) findViewById(R.id.editTextCaptchaString);
		String username = editTextUserName.getText().toString();
		String password = editTextPassword.getText().toString();
		String captchaString = textViewCaptchaString.getText().toString();

		Intent intent = new Intent(this, DashboardActivity.class);
		if (Services.getInstance().getLoginService()
				.login(username, password, captchaString, captchaCode)) {
			startActivity(intent);
		} else {
			// show a dialog not authorized
		}
	}

}
