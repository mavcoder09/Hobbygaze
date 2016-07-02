package com.hobbygaze.maverick.hobbygaze.lractivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.oldfiles.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 11/29/15.
 */
public class ForgotPassword extends Activity {

EditText inputUsername;
    Button submit;
    String url=null;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputUsername=(EditText)findViewById(R.id.username);
        submit=(Button)findViewById(R.id.forgot_psswd);
        inputUsername.setError(null);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUsername.getText().toString().trim();
                //String email = inputEmail.getText().toString().trim();

                if (!(username.contains("@"))) {
                    //Toaster.toastLong("Email id invalid");
                    inputUsername.setError(getString(R.string.error_invalid_email));

                }
                // Check for empty data in the form
                if (!username.isEmpty()) {
                    url = "http://www.hobbygaze.com/api/user/retrieve_password/?user_login="+username;
                    System.out.println("URL= " + url);
                    new Forgot().execute();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });


    }

    /**
     * Async task class to get json by making HTTP call
     * */
    public class Forgot extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String company_email=null;
            String jsonStr = null;
            try {
                jsonStr = sh.run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonStr != null) {
                try {
                    JSONObject jObj = new JSONObject(jsonStr);
                    //boolean error = jObj.getBoolean("error");
                    String status=jObj.getString("status");
                    // Check for error node in json
                    if (status.contentEquals("ok")) {
                        // user successfully logged in
                        // Create login session


                        // Now store the user in SQLite

                        String message=jObj.getString("msg");
                        System.out.println(message);

                        // Inserting row in users table
                        // db.addUser(cookie, id, username, displayname);
                        Toaster.toastLong(message);
                        // Launch main activity
                        Intent intent = new Intent(ForgotPassword.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String error=jObj.getString("error");
                        System.out.println("error=" + error);
                        //showerror(LoginActivity.this, error, new ErrormsgHandler());
                        //errormsg.setVisibility(View.VISIBLE);

                        //errormsg.setText(error);
                        Toaster.toastLong(error);
                        //  Toast.makeText(getApplicationContext(),
                        //       error, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toaster.toastLong(e.getMessage());
                    // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("Service Handler", "Couldn't get any data from the url");
            }
            //System.out.println(response);

            //Log.d("Response: ", "> " + response);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }






















}