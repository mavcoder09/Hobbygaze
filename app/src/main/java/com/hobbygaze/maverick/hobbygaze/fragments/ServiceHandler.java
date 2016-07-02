package com.hobbygaze.maverick.hobbygaze.fragments;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class ServiceHandler {


	public ServiceHandler() {

	}
//for get https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/com/squareup/okhttp/guide/GetExample.java
	OkHttpClient client = new OkHttpClient();

	public String run(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		Response response = client.newCall(request).execute();
		return response.body().string();

	}
//for post use https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/com/squareup/okhttp/guide/PostExample.java

}
