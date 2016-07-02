package com.hobbygaze.maverick.hobbygaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.hobbygaze.maverick.hobbygaze.lractivity.LoginActivity;

import org.json.JSONObject;


import io.fabric.sdk.android.Fabric;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Branch.getAutoInstance(this);
		//Remove title bar
		//requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//Fabric.with(this, new Crashlytics());
		//Fabric.with(this, new Answers());
		setContentView(R.layout.activity_splash);


		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashScreen.this, LoginActivity.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//i.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onNewIntent(Intent intent) {
		this.setIntent(intent);
	}



}
